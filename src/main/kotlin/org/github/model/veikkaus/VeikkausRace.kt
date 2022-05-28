package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausRace(
    @JsonProperty("raceId") val id: Int,
    @JsonProperty("number") val raceNumber: Int,
    @JsonProperty("breed") val breed: String = "L",
    @JsonProperty("startType") val startType: String,
    @JsonProperty("distance") val distance: Int,
)
