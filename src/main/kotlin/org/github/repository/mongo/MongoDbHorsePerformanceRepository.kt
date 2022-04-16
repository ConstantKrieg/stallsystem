package org.github.repository.mongo

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
        eq("track", trackId)
            .also { eq("winner", true) }
            .also { eq("car_start", true) }
    )

    override fun getVoltStartWinsByTrack(trackId: String): Publisher<HorsePerformance> = collection.find(
        eq("track", trackId)
            .also { eq("winner", true) }
            .also { eq("car_start", false) }
    )

    private val collection: MongoCollection<HorsePerformance>
        get() = mongoClient.getDatabase(horsePerformanceConfiguration.name)
            .getCollection(horsePerformanceConfiguration.collection, HorsePerformance::class.java)
}