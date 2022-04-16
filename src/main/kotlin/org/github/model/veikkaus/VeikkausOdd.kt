package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausOdd(
    @JsonProperty("runnerNumber") val horseNumber: Int,
    @JsonProperty("probable") val probable: Int,
)
