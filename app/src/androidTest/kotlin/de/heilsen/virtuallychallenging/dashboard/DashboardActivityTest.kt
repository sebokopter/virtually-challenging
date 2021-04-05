package de.heilsen.virtuallychallenging.dashboard

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class DashboardActivityTest {
    @Rule
    @JvmField
    val activityScenarioRule = activityScenarioRule<DashboardActivity>()

    @Test
    fun startActivity() {
        val scenario = activityScenarioRule.scenario
        assertThat(scenario.state.isAtLeast(Lifecycle.State.STARTED), equalTo(true))
    }
}