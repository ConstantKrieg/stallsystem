package org.github.service

import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.github.client.UnibetClient
import org.github.client.VeikkausClient
import org.github.configuration.UnibetConfiguration
import org.github.configuration.VeikkausConfiguration
import org.github.repository.HorsePerformanceRepository
import org.github.repository.conf.HorsePerformanceConfiguration
import org.github.repository.mongo.MongoDbHorsePerformanceRepository
import org.junit.Test
import java.net.URL

@MicronautTest
internal class VeikkausCardServiceTest(
    val horsePerformanceRepository: HorsePerformanceRepository
) {


    @Test
    fun testFetchVeikkausCards() {
        val veikkausClient =  VeikkausClient(HttpClient.create(URL(VeikkausConfiguration.VEIKKAUS_EVENTS_URL)))
        val unibetClient = UnibetClient(HttpClient.create(URL(UnibetConfiguration.UNIBET_EVENTS_URL)))

        val unibetOddService = UnibetOddService(unibetClient)

        val veikkausCardService = VeikkausCardService(veikkausClient, unibetOddService, horsePerformanceRepository)

        val result = veikkausCardService.fetchCards().block()

        print("AAA")

    }
}