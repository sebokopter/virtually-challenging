package de.heilsen.virtuallychallenging.dashboard

import java.time.Instant
import java.time.temporal.ChronoUnit

object DateProcessor {
    fun consecutiveDays(instants: Iterable<Instant>, start: Instant = Instant.now()): Int {
        val startDay = start.truncatedTo(ChronoUnit.DAYS)
        val days = instants.map { it.truncatedTo(ChronoUnit.DAYS) }
        if (days.isEmpty()) return 0

        if (startDay in days) {
            val previousDay = startDay.minus(1, ChronoUnit.DAYS)
            return 1 + consecutiveDays(days - startDay, previousDay)
        }
        return 0
    }
}