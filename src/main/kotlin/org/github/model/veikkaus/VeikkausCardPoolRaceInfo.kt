package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausCardPoolRaceInfo(
    @JsonProperty("raceId") val raceId: Long,
    @JsonProperty("raceNumber") val raceNumber: Int,
    )
