package org.github.client

import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Singleton
import org.github.configuration.UnibetConfiguration
import reactor.core.publisher.Mono

@Singleton
class UnibetClient( @param:Client private val httpClient: HttpClient ) {

    fun fetchEvents(): Mono<String> {

        val req: HttpRequest<*> = HttpRequest.GET<String>(UnibetConfiguration.UNIBET_EVENTS_URL)
            .header(ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

    fun fetchOddsForSingleEvent(eventId: Long): Mono<String> {
        val req: HttpRequest<*> = HttpRequest.GET<String>(UnibetConfiguration.UNIBET_EVENT_ODDS_URL + eventId)
            .header(ACCEPT, "application/json")

        return Mono.from(httpClient.retrieve(req))
    }

}