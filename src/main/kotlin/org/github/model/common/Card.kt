package org.github.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Card(
    @JsonProperty("trackName") val trackName: String,
    @JsonProperty("trackId") val trackId: String,
    @JsonProperty("races") val races: List<Race>,
    @JsonProperty("trackInfo") var trackInfo: TrackInfo?

    )