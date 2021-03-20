package de.heilsen.virtuallychallenging.dashboard

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

//TODO: don't use Instant.now()
class DateProcessorTest {
    @Test
    fun `no dates returns 0`() {
        val dates = listOf<Instant>()
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(0))
    }

    @Test
    fun `single today returns 1`() {
        val dates = listOf<Instant>(Instant.now())
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(1))
    }

    @Test
    fun `single day (not-today) returns 0`() {
        val dates = listOf<Instant>(Instant.MIN)
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(0))
    }

    @Test
    fun `two consecutive days`() {
        val today = Instant.now()
        val yesterday = today.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(today, yesterday)
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(2))
    }

    @Test
    fun `two consecutive days a while ago`() {
        val firstDay = Instant.now().minus(10, ChronoUnit.DAYS)
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(firstDay, secondDay)
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(0))
    }

    @Test
    fun `three consecutive days`() {
        val today = Instant.now()
        val yesterday = today.minus(1, ChronoUnit.DAYS)
        val dayBeforeYesterday = yesterday.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(today, yesterday, dayBeforeYesterday)
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(3))
    }

    @Test
    fun `three consecutive days a while ago`() {
        val firstDay = Instant.now().minus(10, ChronoUnit.DAYS)
        val secondDay = firstDay.minus(1, ChronoUnit.DAYS)
        val thirdDay = secondDay.minus(1, ChronoUnit.DAYS)
        val dates = listOf<Instant>(firstDay, secondDay, thirdDay)
        assertThat(DateProcessor.consecutiveDays(dates), equalTo(0))
    }

}