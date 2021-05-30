package de.heilsen.virtuallychallenging.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import de.heilsen.virtuallychallenging.domain.LongestStreak
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.test.screen.DashboardScreen
import de.heilsen.virtuallychallenging.util.activityTestRule
import de.heilsen.virtuallychallenging.util.viewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ExperimentalCoroutinesApi
class DashboardActivityTest {
    private val workoutRepository = InMemoryWorkoutRepository
    private val currentTime = LocalDateTime.of(2021, 5, 4, 5, 6)
    private val clock = Clock.fixed(
        Instant.from(currentTime.atZone(ZoneId.systemDefault())),
        ZoneId.systemDefault()
    )
    private val dashboardViewModel = DashboardViewModel(
        workoutRepository,
        LongestStreak(clock),
        TestCoroutineDispatcher()
    )

    @Before
    fun setUp() = runBlockingTest {
        workoutRepository.add(Workout(3.km, LocalDateTime.of(2021, 5, 1, 2, 3), 1))
        workoutRepository.add(Workout(20.km, LocalDateTime.of(2021, 5, 3, 4, 5), 2))
        workoutRepository.add(Workout(100.km, LocalDateTime.of(2021, 5, 4, 5, 6), 3))
    }

    @After
    fun tearDown() {
        workoutRepository.clear()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
            progressText.hasText(String.format("%1$.1f km", 123.km))
        }
    }

    @Test
    fun showsCurrentStreak() {
        onScreen<DashboardScreen> {
            streakText.hasText("2")
        }
    }

    @Test
    fun showsTotalWorkoutCount() {
        onScreen<DashboardScreen> {
            totalWorkoutsText.hasText("3")
        }
    }

    @Test
    fun updateWorkouts() = runBlockingTest {
        onScreen<DashboardScreen> {
            totalWorkoutsText.hasText("3")
        }
        dashboardViewModel.dispatch(DashboardAction.AddWorkout(Workout(234.km, id = 4)))
        onScreen<DashboardScreen> {
            totalWorkoutsText.hasText("4")
        }
    }
}