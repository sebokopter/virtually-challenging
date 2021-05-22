package de.heilsen.virtuallychallenging.domain

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.*
import java.time.temporal.ChronoUnit

class StreakTest {
    private val today = LocalDate.of(2021, 5, 1)
    private val yesterday = today.minus(1, ChronoUnit.DAYS)
    private val tomorrow = today.plus(1, ChronoUnit.DAYS)
    private val dayBeforeYesterday = yesterday.minus(1, ChronoUnit.DAYS)
    private val tenDaysInThePast = today.minus(10, ChronoUnit.DAYS)

    private val clock = Clock.fixed(
        Instant.from(today.atStartOfDay().atZone(ZoneId.systemDefault())),
        ZoneId.systemDefault()
    )
    private val longestStreak = LongestStreak(clock)
    private var dates = emptyList<LocalDate>()

    @Test
    fun `no dates returns 0`() {
        assertThat(longestStreak.current(dates), equalTo(Period.ZERO))
    }

    @Test
    fun `single today returns 1`() {
        dates = listOf(today)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(1)))
    }

    @Test
    fun `single day (not-today) returns 0`() {
        dates = listOf(LocalDate.MIN)
        assertThat(longestStreak.current(dates), equalTo(Period.ZERO))
    }

    @Test
    fun `two consecutive days`() {
        dates = listOf(today, yesterday)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `two consecutive days a while ago`() {
        val firstDay = tenDaysInThePast
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        dates = listOf(firstDay, secondDay)
        assertThat(longestStreak.current(dates), equalTo(Period.ZERO))
    }

    @Test
    fun `three consecutive days`() {
        dates = listOf(today, yesterday, dayBeforeYesterday)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(3)))
    }

    @Test
    fun `three consecutive days a while ago`() {
        val firstDay = tenDaysInThePast
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        val thirdDay = secondDay.minus(1, ChronoUnit.DAYS)
        dates = listOf(firstDay, secondDay, thirdDay)
        assertThat(longestStreak.current(dates), equalTo(Period.ZERO))
    }

    @Test
    fun `two workouts on the same day and another one yesterday`() {
        dates = listOf(today, today, yesterday)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `first entry is yesterday`() {
        dates = listOf(yesterday, today)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `first entry is tomorrow`() {
        dates = listOf(tomorrow, today)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(1)))
    }

    @Test
    fun `current Streak allows 24 hours to continue`() {
        dates = listOf(dayBeforeYesterday, yesterday)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `current Streak`() {
        dates = listOf(dayBeforeYesterday, yesterday, today)
        assertThat(longestStreak.current(dates), equalTo(Period.ofDays(3)))
    }
}