package de.heilsen.virtuallychallenging.domain

import java.time.*
import java.time.temporal.ChronoUnit

fun Collection<LocalDateTime>.consecutiveDays(
    startInstant: Instant = Instant.now(),
    zoneId: ZoneId = ZoneId.systemDefault()
): Period {
    return consecutiveDays(
        dates = map { it.toLocalDate() },
        currentDay = startInstant.atZone(zoneId).toLocalDate()
    )
}

private fun consecutiveDays(
    dates: Collection<LocalDate>,
    currentDay: LocalDate
): Period {
    if (dates.isEmpty()) return Period.ZERO

    val previousDay = currentDay.minus(1, ChronoUnit.DAYS)

    val period: Period = when (currentDay) {
        !in dates -> Period.ZERO
        else -> Period.ofDays(1)
    }

    if (previousDay !in dates) return period

    val datesWithoutCurrent = dates - currentDay
    val consecutiveDays = consecutiveDays(datesWithoutCurrent, previousDay)

    return period + consecutiveDays
}