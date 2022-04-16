package org.github.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.tree.JsonObject
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.github.configuration.UnibetConfiguration
import org.github.model.unibet.UnibetEventResponse
import org.github.model.unibet.UnibetEventWrapper
import org.github.model.unibet.UnibetGroup
import org.github.model.unibet.UnibetMainResponse
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import reactor.core.publisher.Mono
import java.net.URL
import kotlin.reflect.typeOf

@MicronautTest
internal class UnibetClientTest {


    @Inject
    @field:Client("/")
    lateinit var unibetClient: UnibetClient


    @Test
    fun testFetch() {

        unibetClient = UnibetClient(HttpClient.create(URL(UnibetConfiguration.UNIBET_EVENTS_URL)));
        val resp: String = unibetClient.fetchEvents().block();

        val objectMapper = jacksonObjectMapper()

        val response: UnibetMainResponse = objectMapper.readValue(resp, UnibetMainResponse::class.java)


        response.events.forEach { unibetEventWrapper ->

            val id: Long = unibetEventWrapper.event.id
            val eventRespString = unibetClient.fetchOddsForSingleEvent(id).block()

            val unibetEventResponse: UnibetEventResponse = objectMapper.readValue(eventRespString, UnibetEventResponse::class.java)
        }

    }
}