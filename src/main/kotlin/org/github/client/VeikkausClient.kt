package org.github.client

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Singleton
import org.github.configuration.VeikkausConfiguration
import reactor.core.publisher.Mono

@Singleton
class VeikkausClient (@param:Client private val httpClient: HttpClient) {


    fun fetchEvents(): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>(VeikkausConfiguration.VEIKKAUS_EVENTS_URL)
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchPoolsForCard(cardId: Int): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>("${VeikkausConfiguration.VEIKKAUS_EVENT_CARD_URL}/$cardId/pools")
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchRacesForCard(cardId: Int): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>("${VeikkausConfiguration.VEIKKAUS_EVENT_CARD_URL}/$cardId/races")
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchRunnersForRace(raceId: Int): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>("${VeikkausConfiguration.VEIKKAUS_RACE_URL}/$raceId/runners")
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchPoolsForRace(raceId: Int): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>("${VeikkausConfiguration.VEIKKAUS_RACE_URL}/$raceId/pools")
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchOddsForPool(poolId: Long): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>("${VeikkausConfiguration.VEIKKAUS_POOL_URL}/$poolId/odds")
            .header(HttpHeaders.ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }
}