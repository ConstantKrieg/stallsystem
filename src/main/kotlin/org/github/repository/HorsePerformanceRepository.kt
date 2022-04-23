package org.github.repository

import org.github.model.HorsePerformance
import org.reactivestreams.Publisher

interface HorsePerformanceRepository {

    fun list(): Publisher<HorsePerformance>
    fun getVoltStartWinsByTrack(trackId: String): Publisher<HorsePerformance>
    fun getCarStartWinsByTrack(trackId: String): Publisher<HorsePerformance>
    fun getCoachWins(coachName: String): Publisher<Long>
    fun getCoachStarts(coachName: String): Publisher<Long>
    fun getCoachWinsFromMonthAndYear(coachName: String, year: Int, month: Int): Publisher<Long>
    fun getCoachStartsFromMonthAndYear(coachName: String, year: Int, month: Int): Publisher<Long>
}