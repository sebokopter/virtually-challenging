package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository: RoomWorkoutRepository by lazy {
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        RoomWorkoutRepository(workoutDao)
    }

    var uiStateLiveData: LiveData<DashboardUiState> = uiState()

    fun dispatch(action: DashboardAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is DashboardAction.AddWorkout -> {
                withContext(Dispatchers.Main.immediate) {
                    workoutRepository.add(Workout(action.workout.distance, action.workout.date))
                }
            }
        }
    }


    private fun uiState(): LiveData<DashboardUiState> {
        val challenge = 1000.km
        val distanceCovered = workoutRepository.totalDistance().flowOn(Dispatchers.IO)
        val consecutiveDays = workoutRepository.consecutiveDays().flowOn(Dispatchers.IO)
        val stateFlow = distanceCovered.combine(consecutiveDays) { distance, days ->
            DashboardUiState(
                distance, challenge, days
            )
        }
        return stateFlow.asLiveData(viewModelScope.coroutineContext)
    }
}