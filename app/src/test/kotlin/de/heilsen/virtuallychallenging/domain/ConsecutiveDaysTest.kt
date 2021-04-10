package de.heilsen.virtuallychallenging.domain

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.Instant
import java.time.Period
import java.time.temporal.ChronoUnit

//TODO: don't use Instant.now()
class ConsecutiveDaysTest {
    @Test
    fun `no dates returns 0`() {
        val dates = listOf<Instant>()
        assertThat(dates.consecutiveDays(), equalTo(Period.ZERO))
    }

    @Test
    fun `single today returns 1`() {
        val dates = listOf<Instant>(Instant.now())
        assertThat(dates.consecutiveDays(), equalTo(Period.ofDays(1)))
    }

    @Test
    fun `single day (not-today) returns 0`() {
        val dates = listOf<Instant>(Instant.MIN)
        assertThat(dates.consecutiveDays(), equalTo(Period.ZERO))
    }

    @Test
    fun `two consecutive days`() {
        val today = Instant.now()
        val yesterday = today.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(today, yesterday)
        assertThat(dates.consecutiveDays(), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `two consecutive days a while ago`() {
        val firstDay = Instant.now().minus(10, ChronoUnit.DAYS)
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(firstDay, secondDay)
        assertThat(dates.consecutiveDays(), equalTo(Period.ZERO))
    }

    @Test
    fun `three consecutive days`() {
        val today = Instant.now()
        val yesterday = today.minus(1, ChronoUnit.DAYS)
        val dayBeforeYesterday = yesterday.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(today, yesterday, dayBeforeYesterday)
        assertThat(dates.consecutiveDays(), equalTo(Period.ofDays(3)))
    }

    @Test
    fun `three consecutive days a while ago`() {
        val firstDay = Instant.now().minus(10, ChronoUnit.DAYS)
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        val thirdDay = secondDay.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(firstDay, secondDay, thirdDay)
        assertThat(dates.consecutiveDays(), equalTo(Period.ZERO))
    }

    @Test
    fun `two workouts on the same day and another one yesterday`() {
        val firstDay = Instant.now()
        val yesterday = firstDay.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(firstDay, firstDay, yesterday)
        assertThat(dates.consecutiveDays(), equalTo(Period.ofDays(2)))
    }

    @Test
    fun `first entry is yesterday`() {
        val today = Instant.now()
        val yesterday = today.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(yesterday, today)
        assertThat(dates.consecutiveDays(), equalTo(Period.ofDays(2)))
    }

}