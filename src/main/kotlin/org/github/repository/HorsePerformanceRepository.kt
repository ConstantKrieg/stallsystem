package org.github.repository

import org.github.model.HorsePerformance
import org.reactivestreams.Publisher

interface HorsePerformanceRepository {

    fun list(): Publisher<HorsePerformance>
    fun getVoltStartWinsByTrack(trackId: String): Publisher<HorsePerformance>
    fun getCarStartWinsByTrack(trackId: String): Publisher<HorsePerformance>
}