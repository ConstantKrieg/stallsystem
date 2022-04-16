package org.github.service

import org.github.model.common.Card
import reactor.core.publisher.Mono

interface CardService {


    fun fetchCards(): Mono<List<Card>>
}