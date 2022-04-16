package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausCardPool(
    @JsonProperty("poolId") val poolId: Long,
    @JsonProperty("cardId") val cardId: Long,
    @JsonProperty("poolType") val poolType: String,
    @JsonProperty("races") val races: List<VeikkausCardPoolRaceInfo>
    )
