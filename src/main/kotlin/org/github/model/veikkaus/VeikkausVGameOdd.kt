package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausVGameOdd(
    @JsonProperty("raceNumber") val raceNumber: Int,
    @JsonProperty("runnerNumber") val runnerNumber: Int,
    @JsonProperty("runnerId") val runnerId: Long,
    @JsonProperty("percentage") val percentage: Int,
    )
