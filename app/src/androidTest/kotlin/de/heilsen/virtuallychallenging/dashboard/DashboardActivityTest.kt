package de.heilsen.virtuallychallenging.dashboard

import androidx.lifecycle.MutableLiveData
import com.agoda.kakao.screen.Screen.Companion.onScreen
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.test.DashboardScreen
import de.heilsen.virtuallychallenging.util.activityTestRule
import de.heilsen.virtuallychallenging.util.viewModelFactory
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class DashboardActivityTest {
    private val dashboardViewModel = mock<DashboardViewModel> {
        on { model() } doReturn MutableLiveData(DashboardModel(123.km, 1000.km, 12, 3))
    }

    @get:Rule
    val activityTestRule = activityTestRule {
        DashboardActivity(viewModelFactory(dashboardViewModel))
    }

    @Test
    fun showsGoalText() {
        onScreen<DashboardScreen> {
            goalText.hasText("1000 km")
        }
    }

    @Test
    fun showsProgressText() {
        onScreen<DashboardScreen> {
            progressText.hasText("123.0 km")
        }
    }

    @Test
    fun showsCurrentStreak() {
        onScreen<DashboardScreen> {
            streakText.hasText("12")
        }
    }

    @Test
    fun showsTotalWorkoutCount() {
        onScreen<DashboardScreen> {
            totalWorkoutsText.hasText("3")
        }
    }
}