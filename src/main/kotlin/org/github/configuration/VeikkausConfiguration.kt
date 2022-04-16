package org.github.configuration

import jakarta.inject.Singleton

@Singleton
class VeikkausConfiguration {

    companion object {
        const val VEIKKAUS_API_BASE_URL: String = "https://www.veikkaus.fi"
        const val VEIKKAUS_EVENTS_URL: String = "$VEIKKAUS_API_BASE_URL/api/toto-info/v1/cards/today"
        const val VEIKKAUS_EVENT_CARD_URL: String = "$VEIKKAUS_API_BASE_URL/api/toto-info/v1/card"
        const val VEIKKAUS_RACE_URL: String = "$VEIKKAUS_API_BASE_URL/api/toto-info/v1/race"
        const val VEIKKAUS_POOL_URL: String = "$VEIKKAUS_API_BASE_URL/api/toto-info/v1/pool/"
    }
}