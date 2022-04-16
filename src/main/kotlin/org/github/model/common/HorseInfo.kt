package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class HorseInfo(
    @JsonProperty("driverName") val driverName: String,
    @JsonProperty("coachName") val coachName: String,
    @JsonProperty("starts") val starts: Int,
    @JsonProperty("moneyPerRaceThisYear") val moneyPerRaceThisYear: Double,
    @JsonProperty("moneyPerRaceTotal") val moneyPerRaceTotal: Double,
    @JsonProperty("frontShoes") val frontShoes: Boolean,
    @JsonProperty("rearShoes") val rearShoes: Boolean,
)
