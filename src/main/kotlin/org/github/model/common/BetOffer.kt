package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class BetOffer(
    @JsonProperty("type") val type: String,
    @JsonProperty("percentage") var percentage: Float,
    @JsonProperty("odd") var odd: Float,
) {

    init {
        if (percentage == -1.0F) {
            percentage = 1 / odd
        }
        else if (odd == -1.0F) {
            odd = 1 / percentage
        }
    }
}
