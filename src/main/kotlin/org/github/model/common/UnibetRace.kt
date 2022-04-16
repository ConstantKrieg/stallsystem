package org.github.model.common

import org.github.model.unibet.UnibetBetOffer

data class UnibetRace(
    val raceNumber: Int,
    val betOffers: List<UnibetBetOffer>
)
