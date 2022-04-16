package org.github.model.unibet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnibetBetOffer(
    @JsonProperty("betOfferType") val betOfferType: UnibetBetOfferType,
    @JsonProperty("outcomes") val outcomes: List<UnibetOutcome>
)
