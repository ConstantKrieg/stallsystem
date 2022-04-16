package org.github.configuration


import jakarta.inject.Singleton

@Singleton
open class UnibetConfiguration {

    companion object {
        val UNIBET_EVENTS_URL: String = System.getenv("UNIBET_URL")
        val UNIBET_EVENT_ODDS_URL: String = System.getenv("UNIBET_EVENT_URL")
    }
}