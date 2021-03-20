package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.util.sumByFloat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
    private val workoutRepository: WorkoutRepository =
        RoomWorkoutRepository(workoutDao)

    var stateLiveData: MutableLiveData<DashboardState> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val current = totalDistance()
            val challenge = 1000.km

            withContext(Dispatchers.Main.immediate) {
                stateLiveData.postValue(DashboardState(current, challenge, consecutiveDays()))
            }
        }
    }

    private suspend fun consecutiveDays(): Int {
        val instants = workoutDao.getAll().map { it.date }
        return DateProcessor.consecutiveDays(instants)
    }

    fun dispatch(action: DashboardAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is DashboardAction.AddWorkout -> {
                withContext(Dispatchers.Main.immediate) {
                    val dashboardState: DashboardState? = stateLiveData.value
                    workoutRepository.add(action.workout.distance)
                    stateLiveData.postValue(
                        dashboardState?.copy(
                            distanceCovered = dashboardState.distanceCovered.plus(
                                action.workout.distance
                            ),
                            consecutiveDays = consecutiveDays()
                        )
                    )
                }
            }
        }
    }

    private suspend fun totalDistance(): Float {
        return workoutDao.getAll().sumByFloat { it.distance }
    }
}