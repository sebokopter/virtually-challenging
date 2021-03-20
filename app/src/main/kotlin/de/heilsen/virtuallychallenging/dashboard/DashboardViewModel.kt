package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.ChallengeProvider
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.data.datasource.SharedPreferencesChallengeProvider
import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.km
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application.applicationContext,
        DashboardDatabase::class.java,
        "dashboard"
    ).build()
    private val workoutDao = database.workoutDao()
    private val workoutRepository: WorkoutRepository =
        RoomWorkoutRepository(workoutDao)
    private val challengeProvider: ChallengeProvider =
        SharedPreferencesChallengeProvider(application)

    var stateLiveData: MutableLiveData<DashboardState> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val current = workoutRepository.get()
            val challenge = 1000.km
            withContext(Dispatchers.Main.immediate) {
                stateLiveData.value = DashboardState(current, challenge)
            }
        }
    }

    fun dispatch(action: DashboardAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is DashboardAction.AddWorkout -> {
                withContext(Dispatchers.Main.immediate) {
                    val dashboardState: DashboardState? = stateLiveData.value
                    stateLiveData.value =
                        dashboardState?.copy(current = dashboardState.current.plus(action.workout.distance))
                }
                workoutRepository.add(action.workout.distance)
            }
        }
    }
}