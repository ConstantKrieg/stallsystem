package org.github.model.unibet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnibetOutcome(
    @JsonProperty("label") val name: String,
    @JsonProperty("odds") val odds: Int,
    @JsonProperty("startNr") val startNumber: Int
)
