package org.github.service

import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.Cacheable
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.github.repository.HorsePerformanceRepository
import reactor.core.publisher.Mono
import java.util.*

@Singleton
@CacheConfig("coach")
open class CoachStatService (
    private val performanceService: HorsePerformanceRepository
){

    @Cacheable
    open fun getStallForm(coachName: String): Mono<Double> {
        val calendar: Calendar = Calendar.getInstance()
        val currentMonth = calendar[Calendar.MONTH] + 1
        val currentYear = calendar[Calendar.YEAR]

        val prevMonth = if (currentMonth == 1) 12 else currentMonth - 1
        val prevMonthYear = if (currentMonth == 1) currentYear - 1 else currentYear

        val totalStartsMono: Mono<Long> = Mono.from(performanceService.getCoachStarts(coachName))
        val totalWinsMono: Mono<Long> = Mono.from(performanceService.getCoachWins(coachName))

        val currMonthStartsMono: Mono<Long> = Mono.from(performanceService.getCoachStartsFromMonthAndYear(coachName, currentYear, currentMonth))
        val currMonthWinsMono: Mono<Long> = Mono.from(performanceService.getCoachWinsFromMonthAndYear(coachName, currentYear, currentMonth))

        val prevMonthStartsMono: Mono<Long> = Mono.from(performanceService.getCoachStartsFromMonthAndYear(coachName, prevMonthYear, prevMonth))
        val prevMonthWinsMono: Mono<Long> = Mono.from(performanceService.getCoachWinsFromMonthAndYear(coachName, prevMonthYear, prevMonth))


       return Mono.zip(
            totalWinsMono,
            currMonthWinsMono,
            prevMonthWinsMono,
            totalStartsMono,
            currMonthStartsMono,
            prevMonthStartsMono
        ).flatMap { results ->

            val totalWins = results.t1
            val recentWins = results.t2 + results.t3
            val totalStarts = results.t4
            val recentStarts = results.t5 + results.t6

            if (totalStarts == 0L || recentStarts == 0L) {
                return@flatMap Mono.just(0.0)
            }

            val totalWinPercentage: Double = totalWins.toDouble() / totalStarts.toDouble() * 100
            val recentWinPercentage: Double = recentWins.toDouble() / recentStarts.toDouble() * 100

            Mono.just(recentWinPercentage - totalWinPercentage)
        }.cache()
    }


}