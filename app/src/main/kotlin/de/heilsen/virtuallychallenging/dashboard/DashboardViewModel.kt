package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.LongestStreak
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.domain.model.sumDistance
import de.heilsen.virtuallychallenging.util.ViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Period

class DashboardViewModel(
    private val workoutRepository: WorkoutRepository,
    private val longestStreak: LongestStreak = LongestStreak(Clock.systemDefaultZone())
) : ViewModel() {

    fun dispatch(action: DashboardAction) = viewModelScope.launch {
        when (action) {
            is DashboardAction.AddWorkout -> with(action.workout) {
                workoutRepository.add(Workout(distance, date))
            }
        }
    }

    val model: LiveData<DashboardModel> = modelFlow().asLiveData(viewModelScope.coroutineContext)

    private fun modelFlow(): Flow<DashboardModel> {
        val goal = 1000.km
        return workoutRepository.workouts()
            .map(toDashboardModel(goal))
    }

    private fun toDashboardModel(challenge: Float):
            suspend (value: List<Workout>) -> DashboardModel =
        { workouts ->
            val consecutiveDays = streak(workouts).days
            val distance = workouts.sumDistance()
            DashboardModel(distance, challenge, consecutiveDays, workouts.size)
        }

    private fun streak(workouts: List<Workout>): Period {
        val dates = workouts.map { it.date.toLocalDate() }
        return longestStreak.current(dates)
    }

    class Factory(private val application: Application) : ViewModelFactory<DashboardViewModel>({
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        val workoutRepository = RoomWorkoutRepository(workoutDao)
        DashboardViewModel(workoutRepository)
    })
}