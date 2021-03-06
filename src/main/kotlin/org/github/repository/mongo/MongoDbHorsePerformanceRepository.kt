package org.github.repository.mongo

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import jakarta.inject.Singleton
import org.github.model.HorsePerformance
import org.github.repository.HorsePerformanceRepository
import org.github.repository.conf.HorsePerformanceConfiguration
import org.reactivestreams.Publisher

@Singleton
open class MongoDbHorsePerformanceRepository(
    private val horsePerformanceConfiguration: HorsePerformanceConfiguration,
    private val mongoClient: MongoClient
) : HorsePerformanceRepository {

    override fun list(): Publisher<HorsePerformance> = collection.find()

    override fun getCarStartWinsByTrack(trackId: String): Publisher<HorsePerformance> = collection.find(
        and(
            eq("track", trackId),
            eq("winner", true),
            eq("car_start", true)
        )
    )

    override fun getVoltStartWinsByTrack(trackId: String): Publisher<HorsePerformance> = collection.find(
        and(
            eq("track", trackId),
            eq("winner", true),
            eq("car_start", false)
        )
    )

    override fun getCoachWins(coachName: String): Publisher<Long> = collection.countDocuments(
        and(
            eq("coachName", coachName),
            eq("winner", true)
        )
    )

    override fun getCoachStarts(coachName: String): Publisher<Long> = collection.countDocuments(
        eq("coachName", coachName)
    )

    override fun getCoachWinsFromMonthAndYear(coachName: String, year: Int, month: Int): Publisher<Long> = collection.countDocuments(
        and(
            eq("coachName", coachName),
            eq("winner", true),
            eq("month", month),
            eq("year", year)
        )
    )

    override fun getCoachStartsFromMonthAndYear(coachName: String, year: Int, month: Int): Publisher<Long> = collection.countDocuments(
        and(
            eq("coachName", coachName),
            eq("month", month),
            eq("year", year)
        )
    )

    private val collection: MongoCollection<HorsePerformance>
        get() = mongoClient.getDatabase(horsePerformanceConfiguration.name)
            .getCollection(horsePerformanceConfiguration.collection, HorsePerformance::class.java)
}