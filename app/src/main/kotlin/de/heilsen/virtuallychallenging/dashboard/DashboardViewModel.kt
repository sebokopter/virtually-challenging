package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.domain.LongestStreak
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.util.sumByFloat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Period

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository: RoomWorkoutRepository by lazy {
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        RoomWorkoutRepository(workoutDao)
    }
    private val longestStreak = LongestStreak()

    fun dispatch(action: DashboardAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is DashboardAction.AddWorkout -> {
                withContext(Dispatchers.Main.immediate) {
                    workoutRepository.add(
                        Workout(action.workout.distance, action.workout.date)
                    )
                }
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

    private fun List<Workout>.sumDistance(): Float =
        sumByFloat { workout -> workout.distance.toFloat() }
}