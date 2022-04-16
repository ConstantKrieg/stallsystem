package org.github.service

import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.github.client.UnibetClient
import org.github.configuration.UnibetConfiguration
import org.junit.Test
import java.net.URL

@MicronautTest
internal class UnibetOddServiceTest {

    @Test
    fun testFetchUnibetCards() {
        val unibetClient = UnibetClient(HttpClient.create(URL(UnibetConfiguration.UNIBET_EVENTS_URL)));

        val unibetOddService = UnibetOddService(unibetClient)

        val result = unibetOddService.fetchUnibetOdds().block()

        print("AAA")

    }

}