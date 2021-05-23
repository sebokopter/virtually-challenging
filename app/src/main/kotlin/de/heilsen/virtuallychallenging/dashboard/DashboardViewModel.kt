package de.heilsen.virtuallychallenging.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.LongestStreak
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.domain.model.sumDistance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Period

class DashboardViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {

    private val longestStreak = LongestStreak()

    fun dispatch(action: DashboardAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is DashboardAction.AddWorkout -> with(action.workout) {
                workoutRepository.add(Workout(distance, date))
            }
        }
    }

    fun model(): LiveData<DashboardModel> {
        val challenge = 1000.km
        return workoutRepository.workouts().map { workouts ->
            val consecutiveDays = streak(workouts).days
            val distance = workouts.sumDistance()
            DashboardModel(distance, challenge, consecutiveDays, workouts.size)
        }.asLiveData(viewModelScope.coroutineContext)
    }

    private fun streak(workouts: List<Workout>): Period {
        val dates = workouts.map { it.date.toLocalDate() }
        return longestStreak.current(dates)
    }

}