package de.heilsen.virtuallychallenging.domain

import java.time.*
import java.time.temporal.ChronoUnit

fun Collection<LocalDateTime>.consecutiveDays(
    startInstant: Instant = Instant.now(),
    zoneId: ZoneId = ZoneId.systemDefault()
): Period {
    return map { it.toLocalDate() }
        .consecutiveDays(startInstant.atZone(zoneId).toLocalDate())
}

private fun Collection<LocalDate>.consecutiveDays(
    start: LocalDate
): Period {
    if (isEmpty()) return Period.ZERO
    val dayBefore = start.minus(1, ChronoUnit.DAYS)

    if (start !in this) return Period.ZERO

    return Period.ofDays(1) + (this - start).consecutiveDays(start = dayBefore)
}