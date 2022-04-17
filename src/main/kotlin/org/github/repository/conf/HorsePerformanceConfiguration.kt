package org.github.repository.conf

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("horse_perf")
interface HorsePerformanceConfiguration : Named {
    val collection: String
}