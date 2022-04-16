package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausEvent(
    @JsonProperty("cardId") val id: Int,
    @JsonProperty("trackName") val name: String,
    @JsonProperty("trackAbbreviation") val trackId: String,
    @JsonProperty("country") val country: String
)
