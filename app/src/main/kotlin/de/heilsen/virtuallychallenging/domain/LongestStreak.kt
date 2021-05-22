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
    fun current(dates: Iterable<LocalDate>): Period = current(dates.toSortedSet())

    private fun current(dates: SortedSet<LocalDate>): Period {
        val date: LocalDate = LocalDate.now(clock)
        return if (hasCurrentStreak(dates)) {
            latest(dates)
        } else {
            latest(dates, yesterdayAsOf(date))
        }
    }

    /**
     * Finds the consecutive Period starting from [date] backwards.
     * Ignores future days.
     */
    private fun latest(
        dates: SortedSet<LocalDate>,
        date: LocalDate = LocalDate.now(clock)
    ): Period {
        if (dates.isEmpty()) return Period.ZERO
        if (!hasCurrentStreak(dates, date)) return Period.ZERO

        val yesterday = yesterdayAsOf(date)
        return Period.ofDays(1) + latest(dates, yesterday)
    }

    private fun hasCurrentStreak(
        dates: SortedSet<LocalDate>,
        date: LocalDate = LocalDate.now(clock)
    ) = date in dates

    private fun yesterdayAsOf(date: LocalDate = LocalDate.now(clock)): LocalDate =
        date.minus(1, ChronoUnit.DAYS)

}