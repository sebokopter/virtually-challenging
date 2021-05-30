package de.heilsen.virtuallychallenging.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.dashboard.DashboardAction
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WorkoutListViewModel @JvmOverloads constructor(
    application: Application,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AndroidViewModel(application) {
    private val workoutRepository: RoomWorkoutRepository by lazy {
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        RoomWorkoutRepository(workoutDao)
    }

    fun model(): LiveData<WorkoutListModel> {
        return workoutRepository.workouts().map { workouts ->
            WorkoutListModel(workouts)
        }.asLiveData(viewModelScope.coroutineContext)
    }

    fun dispatch(action: DashboardAction) = viewModelScope.launch(coroutineDispatcher) {
        when (action) {
            is DashboardAction.DeleteWorkout -> {
                workoutRepository.delete(action.workout)
            }
        }
    }
}
