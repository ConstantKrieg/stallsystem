package org.github.model.unibet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnibetEventPath(
    @JsonProperty("englishName") val label: String
)
