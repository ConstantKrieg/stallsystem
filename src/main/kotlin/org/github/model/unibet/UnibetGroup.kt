package org.github.model.unibet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnibetGroup(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("events") val events: List<UnibetEventWrapper>
)
