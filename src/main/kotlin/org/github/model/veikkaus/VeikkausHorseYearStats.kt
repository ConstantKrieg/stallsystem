package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausHorseYearStats(
    @JsonProperty("year") val year: String,
    @JsonProperty("starts") val starts: Int,
    @JsonProperty("position1") val wins: Int,
    @JsonProperty("winMoney") val winMoney: Int
)