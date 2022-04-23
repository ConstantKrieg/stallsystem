package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Horse(
    @JsonProperty("startNumber") val startNumber: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("info") val info: HorseInfo,
    @JsonProperty("stallForm") val stallForm: Double,
    @JsonProperty("betOffers") val betOffers: List<BetOffer>
)
