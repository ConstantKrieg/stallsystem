package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Race(
    @JsonProperty("raceNumber") val raceNumber: Int,
    @JsonProperty("horses") val horses: List<Horse>,
    @JsonProperty("breed") val breed: String?,
    @JsonProperty("distance") val distance: Int?,
    @JsonProperty("startType") val startType: String?
)
