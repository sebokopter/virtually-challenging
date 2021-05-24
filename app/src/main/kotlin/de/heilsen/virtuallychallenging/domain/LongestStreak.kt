package de.heilsen.virtuallychallenging.domain

import java.time.Clock
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*

class LongestStreak(private val clock: Clock = Clock.systemDefaultZone()) {

    /**
     * Finds the consecutive Period starting from today, i.e. LocalDate.now(clock)
     * Returns old streak for up to 24 hours. I.e. you get yesterdays streak even if today is not
     * included.
     */
    fun current(
        dates: Iterable<LocalDate>,
        today: LocalDate = LocalDate.now(clock)
    ): Period = current(dates.toSortedSet(), today)

    private fun current(
        dates: SortedSet<LocalDate>,
        today: LocalDate = LocalDate.now(clock)
    ): Period {
        return if (hasCurrentStreak(dates)) {
            latest(dates, today)
        } else {
            latest(dates, yesterdayAsOf(today))
        }
    }

    /**
     * Finds the consecutive Period starting from [today] backwards.
     * Ignores future days.
     */
    private fun latest(
        dates: SortedSet<LocalDate>,
        today: LocalDate = LocalDate.now(clock)
    ): Period {
        if (dates.isEmpty()) return Period.ZERO
        if (!hasCurrentStreak(dates, today)) return Period.ZERO

        val yesterday = yesterdayAsOf(today)
        return Period.ofDays(1) + latest(dates, yesterday)
    }

    private fun hasCurrentStreak(
        dates: SortedSet<LocalDate>,
        today: LocalDate = LocalDate.now(clock)
    ) = today in dates

    private fun yesterdayAsOf(today: LocalDate = LocalDate.now(clock)): LocalDate =
        today.minus(1, ChronoUnit.DAYS)

}