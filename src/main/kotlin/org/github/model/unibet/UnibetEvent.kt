package org.github.model.unibet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnibetEvent(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("group") val trackName: String,
    @JsonProperty("path") val path: List<UnibetEventPath>
)
