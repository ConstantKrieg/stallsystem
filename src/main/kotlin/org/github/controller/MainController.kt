package org.github.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.github.model.common.Card
import org.github.service.VeikkausCardService
import reactor.core.publisher.Mono

@Controller
open class MainController (
    private val veikkausCardService: VeikkausCardService
    ) {

    @Get("/get-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun list(): Mono<List<Card>> {
        return veikkausCardService.fetchCards()
    }
}