package org.github.model.veikkaus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VeikkausCardPoolResp(
    @JsonProperty("collection") val veikkausCardPools: List<VeikkausCardPool>
)
