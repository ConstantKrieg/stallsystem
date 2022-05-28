package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausHorse(
    @JsonProperty("runnerId") val id: Long,
    @JsonProperty("horseName") val name: String,
    @JsonProperty("startNumber") val startNumber: Int,
    @JsonProperty("startTrack") val startTrack: Int,
    @JsonSetter(nulls = Nulls.SKIP) @JsonProperty("frontShoes") val frontShoes: String?,
    @JsonSetter(nulls = Nulls.SKIP) @JsonProperty("rearShoes") val rearShoes: String?,
    @JsonSetter(nulls = Nulls.SKIP) @JsonProperty("driverName") var driverName: String?,
    @JsonSetter(nulls = Nulls.SKIP) @JsonProperty("coachName") val coachName: String?,
    @JsonSetter(nulls = Nulls.SKIP) @JsonProperty("stats") val stats: VeikkausHorseStats?
)
