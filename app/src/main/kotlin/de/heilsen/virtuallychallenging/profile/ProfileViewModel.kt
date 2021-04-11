package de.heilsen.virtuallychallenging.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import kotlinx.coroutines.flow.map

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository: RoomWorkoutRepository by lazy {
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        RoomWorkoutRepository(workoutDao)
    }

    fun model(): LiveData<ProfileModel> {
        return workoutRepository.workouts().map { workouts ->
            ProfileModel(workouts)
        }.asLiveData(viewModelScope.coroutineContext)
    }

}
