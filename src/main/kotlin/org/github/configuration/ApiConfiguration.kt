package org.github.configuration

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("horse_perf")
interface ApiConfiguration : Named {
    val unibet: String
    val veikkaus: String
}