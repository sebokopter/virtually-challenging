package de.heilsen.virtuallychallenging.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.domain.LongestStreak
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.test.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.kotlin.coroutines.test.junit4.MainCoroutineRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val inMemoryRepository = InMemoryWorkoutRepository

    private val workout = Workout(
        distance = 123.km,
        date = LocalDateTime.of(2021, 5, 1, 12, 34),
        id = 0
    )
    private val yesterdaysWorkout =
        Workout(distance = 123.km, date = LocalDateTime.of(2021, 4, 30, 12, 34))
    private val dayBeforeYesterdaysWorkout =
        Workout(distance = 123.km, date = LocalDateTime.of(2021, 4, 29, 12, 34))

    private val currentTime = LocalDateTime.of(2021, 5, 1, 23, 45, 0).atZone(ZoneId.systemDefault())
    private val currentInstant = Instant.from(currentTime)

    private lateinit var viewModel: DashboardViewModel // lateinit because it does coroutine calls on construction

    @Before
    fun setUp() {
        viewModel = DashboardViewModel(
            inMemoryRepository,
            LongestStreak(Clock.fixed(currentInstant, ZoneId.systemDefault())),
            TestCoroutineDispatcher()
        )
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
        inMemoryRepository.clear()
    }

    @Test
    fun dispatchAddWorkoutAction() = runBlockingTest {
        viewModel.dispatch(DashboardAction.AddWorkout(workout))

        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(workout.distance.toFloat(), 1000.km, 1, 1))
            )
        }
    }

    @Test
    fun initialDashboardModel() = runBlockingTest {
        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(0.km, 1000.km, 0, 0))
            )
        }
    }

    @Test
    fun addTodaysWorkout() = runBlockingTest {
        viewModel.dispatch(DashboardAction.AddWorkout(workout))
        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(123.km, 1000.km, 1, 1))
            )
        }
    }

    @Test
    fun addYesterdaysWorkout() = runBlockingTest {
        viewModel.dispatch(DashboardAction.AddWorkout(yesterdaysWorkout))
        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(123.km, 1000.km, 1, 1))
            )
        }
    }

    @Test
    fun addDayBeforeYesterdaysWorkout() = runBlockingTest {
        viewModel.dispatch(DashboardAction.AddWorkout(dayBeforeYesterdaysWorkout))
        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(123.km, 1000.km, 0, 1))
            )
        }
    }

    @Test
    fun addMultipleWorkouts() = runBlockingTest {
        viewModel.dispatch(DashboardAction.AddWorkout(workout))
        viewModel.dispatch(DashboardAction.AddWorkout(yesterdaysWorkout))
        viewModel.dispatch(DashboardAction.AddWorkout(dayBeforeYesterdaysWorkout))
        viewModel.model.observeForTesting {
            assertThat(
                viewModel.model.value,
                equalTo(DashboardModel(123.km * 3, 1000.km, 3, 3))
            )
        }
    }

}