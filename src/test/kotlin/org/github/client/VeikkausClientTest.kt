package org.github.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.tree.JsonObject
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.github.configuration.UnibetConfiguration
import org.github.configuration.VeikkausConfiguration
import org.github.model.unibet.UnibetEventResponse
import org.github.model.unibet.UnibetGroup
import org.github.model.unibet.UnibetMainResponse
import org.github.model.veikkaus.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import reactor.core.publisher.Mono
import java.net.URL
import kotlin.reflect.typeOf

@MicronautTest
internal class VeikkausClientTest {


    @Inject
    @field:Client("/")
    lateinit var veikkausClient: VeikkausClient


    @Test
    fun testFetch() {

        veikkausClient = VeikkausClient(HttpClient.create(URL(VeikkausConfiguration.VEIKKAUS_EVENTS_URL)));
        val respString: String = veikkausClient.fetchEvents().block();

        val objectMapper = jacksonObjectMapper()

        val veikkausMainResponse: VeikkausMainResponse = objectMapper.readValue(respString, VeikkausMainResponse::class.java)

        veikkausMainResponse.events.forEach { event ->

            val poolRespString: String = veikkausClient.fetchPoolsForCard(event.id).block()

            val poolResp: VeikkausCardPoolResp = objectMapper.readValue(poolRespString, VeikkausCardPoolResp::class.java)

            val tPools: List<VeikkausCardPool> = poolResp.veikkausCardPools.filter { pool -> pool.poolType.matches(Regex("T[0-9]{1,2}")) }

            val oddRespString: String = veikkausClient.fetchOddsForPool(tPools.first().poolId).block()

            print("!")
        }
        print("aa")

    }
}