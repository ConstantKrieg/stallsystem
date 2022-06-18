package org.github.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.github.client.VeikkausClient
import org.github.model.HorsePerformance
import org.github.model.common.*
import org.github.model.veikkaus.*
import org.github.repository.HorsePerformanceRepository
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import java.util.*

@Singleton
class VeikkausCardService (
    private val veikkausClient: VeikkausClient,
    private val unibetOddService: UnibetOddService,
    private val coachStatService: CoachStatService,
    private val performanceService: HorsePerformanceRepository
    ): CardService {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Scheduled(cron = "0 0 9 1/1 * ?")
    fun buildCache(){
        fetchCards().subscribe()
    }

    override fun fetchCards(): Mono<List<Card>> {

        return unibetOddService.fetchUnibetOdds().flatMap { unibetOdds ->
            return@flatMap veikkausClient.fetchEvents()
                .flatMap { responseString ->
                    val mainResponse: VeikkausMainResponse = objectMapper.readValue(responseString, VeikkausMainResponse::class.java)

                    val cardMonos: List<Mono<Card>> = mainResponse.events.filter { it.country == "SE" }.map { veikkausEvent ->
                        fetchRacesForCard(veikkausEvent.name, veikkausEvent.trackId, veikkausEvent.id, unibetOdds)
                    }

                    return@flatMap Flux.fromIterable(cardMonos).flatMap { card -> card }.collectList()
                }
        }

    }

    private fun getCarStartWins(trackId: String): Mono<Map<Int, Float>> {
        val carStartWins: Flux<HorsePerformance> = Flux.from(performanceService.getCarStartWinsByTrack(trackId))
        val carWinsMono: Mono<List<HorsePerformance>> = carStartWins.collectList()

        return carWinsMono.flatMap { wins ->
            Mono.just(wins.groupingBy { it.startTrack }.eachCount().mapValues { entry -> entry.value.toFloat() / wins.size  }
            )
        }
    }

    private fun getVoltStartWins(trackId: String): Mono<Map<Int, Float>> {
        val carStartWins: Flux<HorsePerformance> = Flux.from(performanceService.getVoltStartWinsByTrack(trackId))
        val carWinsMono: Mono<List<HorsePerformance>> = carStartWins.collectList()

        return carWinsMono.flatMap { wins ->
            Mono.just(wins.groupingBy { it.startTrack }.eachCount().mapValues { entry -> entry.value.toFloat() / wins.size  })
        }
    }

    private fun fetchRacesForCard(name: String, trackId: String,  cardId: Int, unibetOdds: Map<String, BetOffer>): Mono<Card> {

        return veikkausClient.fetchPoolsForCard(cardId)
            .flatMap vGameOddsFetch@{ poolRespString ->

                val poolResp: VeikkausCardPoolResp = objectMapper.readValue(poolRespString, VeikkausCardPoolResp::class.java)
                val tPools: List<VeikkausCardPool> = poolResp.veikkausCardPools.filter { pool -> pool.poolType.matches(Regex("T[0-9]{1,2}")) }

                val oddsForTPools: List<Mono<Map<Long, BetOffer>>> = tPools.map { veikkausCardPool ->
                    fetchOddsForVGameByPoolId(veikkausCardPool.poolType, veikkausCardPool.poolId)
                }

                val monoVPools: Mono<List<Map<Long, BetOffer>>> = Flux.fromIterable(oddsForTPools).flatMap { oddsMap -> oddsMap }.collectList()

                return@vGameOddsFetch monoVPools.flatMap cardFetch@{ vPools ->
                    veikkausClient.fetchRacesForCard(cardId)
                        .flatMap { raceResp ->

                            val veikkausRace: VeikkausRaceResp = objectMapper.readValue(raceResp, VeikkausRaceResp::class.java)
                            val raceMonos: List<Mono<Race>> = veikkausRace.races.map { veikkausRace ->
                                return@map fetchWinnerOddsFor(veikkausRace.id)
                                    .flatMap { oddsMap -> fetchHorsesForRace(veikkausRace.id, oddsMap, vPools, unibetOdds)}
                                    .flatMap { horses ->  Mono.just(Race(veikkausRace.raceNumber, horses, veikkausRace.breed, veikkausRace.distance, veikkausRace.startType))}
                            }

                            // Switch the type from List<Mono> -> Mono<List>
                            val monoRaces: Mono<List<Race>> = Flux.fromIterable(raceMonos).flatMap { race -> race }.collectList()

                            return@flatMap monoRaces.flatMap { races ->
                                Mono.just(Card(name, trackId, races, null))
                            }.flatMap { card ->

                                val carStartMono: Mono<Map<Int, Float>> = getCarStartWins(trackId)
                                val voltWinsMono: Mono<Map<Int, Float>> = getVoltStartWins(trackId)

                                Mono.zip(carStartMono, voltWinsMono)
                                    .flatMap { winMaps ->

                                        val trackInfo = TrackInfo(winMaps.t1, winMaps.t2)
                                        card.trackInfo = trackInfo
                                        Mono.just(card)
                                    }
                            }
                        }
                }
            }

    }


    private fun fetchOddsForVGameByPoolId(poolType: String, poolId: Long): Mono<Map<Long, BetOffer>> {
        return veikkausClient.fetchOddsForPool(poolId)
            .flatMap { respString ->

                val vGameOddsResp: VeikkausVGameOddResp = objectMapper.readValue(respString, VeikkausVGameOddResp::class.java)
                val oddsMap: MutableMap<Long, BetOffer> = mutableMapOf()

                vGameOddsResp.odds.forEach { odd ->
                    oddsMap[odd.runnerId] = BetOffer(poolType, odd.percentage.toFloat() / 10000, -1F)
                }

                return@flatMap Mono.just(oddsMap)
            }
    }

    private fun fetchWinnerOddsFor(raceId: Int): Mono<MutableMap<Int, Double>> {

        return veikkausClient.fetchPoolsForRace(raceId)
            .flatMap { poolsRespString ->

                val poolResp: VeikkausPoolResp = objectMapper.readValue(poolsRespString, VeikkausPoolResp::class.java)

                val winnerPool: VeikkausPool = poolResp.pools.find { veikkausPool ->  veikkausPool.type == "VOI" }
                    ?: return@flatMap Mono.just(-1)

                return@flatMap Mono.just(winnerPool.id)
            }
            .flatMap outer@ { winnerPoolId ->

                if (winnerPoolId == -1L) return@outer Mono.just(mutableMapOf())

                return@outer veikkausClient.fetchOddsForPool(winnerPoolId)
                    .flatMap inner@ {  oddsRespString ->
                        val oddsResp: VeikkausOddResp = objectMapper.readValue(oddsRespString, VeikkausOddResp::class.java)
                        val oddsMap: MutableMap<Int, Double> = mutableMapOf()
                        oddsResp.odds.forEach { odd ->
                            oddsMap[odd.horseNumber] = odd.probable.toDouble() / 100
                        }
                        return@inner Mono.just(oddsMap)
                    }
            }
    }

    private fun fetchHorsesForRace(raceId: Int, oddsMap: MutableMap<Int, Double>, vGamesOddsMaps: List<Map<Long, BetOffer>>, unibetOdds: Map<String, BetOffer>): Mono<List<Horse>> {
        return veikkausClient.fetchRunnersForRace(raceId)
            .flatMap { horseResponseString ->

                val veikkausRunnersResp: VeikkausRunnersResp = objectMapper.readValue(horseResponseString, VeikkausRunnersResp::class.java)

                val horses: List<Mono<Horse>> = veikkausRunnersResp.horses.map collectHorses@{ runner ->
                    val odds: Double =  oddsMap.getOrDefault(runner.startNumber, -1.0)
                    val hasFrontShoes: Boolean = runner.frontShoes == "HAS_SHOES"
                    val hasRearShoes: Boolean = runner.rearShoes == "HAS_SHOES"


                    var totalMoneyPerStart = 0.0
                    var currentYearMoneyPerStart = 0.0
                    var totalStarts = 0

                    if (runner.stats != null) {
                        totalMoneyPerStart = runner.stats.total.winMoney.toDouble() / runner.stats.total.starts.toDouble() / 100
                        currentYearMoneyPerStart = runner.stats.currentYear.winMoney.toDouble() / runner.stats.currentYear.starts.toDouble() / 100
                        totalStarts = runner.stats.total.starts
                    }


                    val betOffers: MutableList<BetOffer> = vGamesOddsMaps.mapNotNull { vGameOdds ->
                        vGameOdds.getOrDefault(runner.id, null)
                    }.toMutableList()

                    val unibetName: String = runner.name.split("*")[0] // Veikkaus usually ends horse names with asterisks and the country they were born in. We take that part out here
                    unibetOdds[unibetName.lowercase(Locale.getDefault())]?.let { betOffers.add(it) }

                    betOffers.add(BetOffer("WINNER", -1F, odds.toFloat()))

                    val driverName: String = runner.driverName ?: "-"
                    val coachName: String = runner.coachName ?: "-"

                    val info = HorseInfo(driverName, coachName, totalStarts, currentYearMoneyPerStart, totalMoneyPerStart, hasFrontShoes, hasRearShoes)

                    return@collectHorses coachStatService.getStallForm(coachName).flatMap { stallForm ->
                         Mono.just(Horse(runner.startNumber, runner.name, info, stallForm, betOffers))
                    }
                }

                return@flatMap Flux.fromIterable(horses).flatMap { horse -> horse }.collectList()
            }
    }
}