package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class TrackInfo(
    @JsonProperty("carStartWinPercentages") val carStartWinPercentages: Map<Int, Float>,
    @JsonProperty("voltStartWinPercentages") val voltStartWinPercentages: Map<Int, Float>
)
