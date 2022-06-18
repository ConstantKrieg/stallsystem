package org.github.service

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton
import org.github.client.UnibetClient
import org.github.model.unibet.UnibetMainResponse
import reactor.core.publisher.Mono
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.github.model.common.BetOffer
import org.github.model.unibet.UnibetBetOffer
import org.github.model.unibet.UnibetEvent
import org.github.model.unibet.UnibetEventResponse
import reactor.core.publisher.Flux
import java.util.*

@Singleton
class UnibetOddService (
    private val unibetClient: UnibetClient,
    ){


    val objectMapper: ObjectMapper = jacksonObjectMapper()


    fun fetchUnibetOdds(): Mono<Map<String, BetOffer>> {

        return unibetClient.fetchEvents().flatMap { responseString ->
            val unibetResponse: UnibetMainResponse = objectMapper.readValue(responseString, UnibetMainResponse::class.java)
            val trackToEventIds: MutableMap<String, MutableList<UnibetEvent>> = mutableMapOf()

           val cardIds: List<Long> = unibetResponse.events.filter { unibetEventWrapper ->
               val pathLabels: List<String> = unibetEventWrapper.event.path.map { it.label }
               return@filter pathLabels.contains("Sweden") && !pathLabels.contains("Elitloppet") // We are only interested in the races that are occurring in Sweden
           }.map { unibetEventWrapper -> unibetEventWrapper.event.id}

            return@flatMap fetchOddsForEvents(cardIds)
        }
    }


    private fun fetchOddsForEvents(ids: List<Long>):Mono<Map<String, BetOffer>> {
        val monoList: List<Mono<Map<String, BetOffer>>> = ids.map { fetchOddsForSingleEvent(it) }

        return Flux.concat(monoList).collectList()
            .flatMap { oddsMapList ->

                val cardOddsMap = oddsMapList
                    .flatMap { it.entries }
                    .associate { it.key.lowercase(Locale.getDefault()) to it.value }

                return@flatMap Mono.just(cardOddsMap)
            }
    }

    private fun fetchOddsForSingleEvent(id: Long):Mono<Map<String, BetOffer>> {
        return unibetClient.fetchOddsForSingleEvent(id).flatMap { eventRespString ->

            val oddsMap: MutableMap<String, BetOffer> = mutableMapOf()

            val unibetEventResponse: UnibetEventResponse = objectMapper.readValue(eventRespString, UnibetEventResponse::class.java)
            val winnerOffers: List<UnibetBetOffer> = unibetEventResponse.betOffers.filter { unibetBetOffer -> unibetBetOffer.betOfferType.name == "Winner"}

            if (winnerOffers.size != 1) {
                print("Error encountered")

                return@flatMap Mono.just(mapOf())
            }

            val offer = winnerOffers.first()
            offer.outcomes.forEach { unibetOutcome ->
                oddsMap[unibetOutcome.name] = BetOffer("UNIBET-WINNER", -1.0F, unibetOutcome.odds.toFloat() / 1000F)
            }

            return@flatMap Mono.just(oddsMap)
        }
    }


}