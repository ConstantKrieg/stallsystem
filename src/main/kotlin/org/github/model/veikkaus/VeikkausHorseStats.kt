package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausHorseStats(
    @JsonProperty("currentYear") val currentYear: VeikkausHorseYearStats,
    @JsonProperty("total") val total: VeikkausHorseYearStats
)
