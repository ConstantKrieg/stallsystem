package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausHorse(
    @JsonProperty("runnerId") val id: Long,
    @JsonProperty("horseName") val name: String,
    @JsonProperty("startNumber") val startNumber: Int,
    @JsonProperty("startTrack") val startTrack: Int,
    @JsonProperty("frontShoes") val frontShoes: String,
    @JsonProperty("rearShoes") val rearShoes: String,
    @JsonProperty("driverName") val driverName: String,
    @JsonProperty("coachName") val coachName: String,
    @JsonProperty("specialCart") val specialCart: String,
    @JsonProperty("stats") val stats: VeikkausHorseStats
)
