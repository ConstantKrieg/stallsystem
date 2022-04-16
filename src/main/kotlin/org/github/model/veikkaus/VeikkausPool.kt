package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausPool(
    @JsonProperty("poolId") val id: Long,
    @JsonProperty("poolType") val type: String,
)
