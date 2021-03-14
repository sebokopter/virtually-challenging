package de.heilsen.virtuallychallenging.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import de.heilsen.virtuallychallenging.data.datasource.ChallengeProvider
import de.heilsen.virtuallychallenging.data.datasource.SharedPreferencesChallengeProvider
import de.heilsen.virtuallychallenging.data.datasource.SharedPreferencesWorkoutRepository
import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.km

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository: WorkoutRepository =
        SharedPreferencesWorkoutRepository(application)
    private val challengeProvider: ChallengeProvider =
        SharedPreferencesChallengeProvider(application)

    val stateLiveData: DashboardState.LiveData

    init {
        val current = workoutRepository.get()
        val challenge = 1000.km
        stateLiveData = DashboardState.LiveData(DashboardState(current, challenge))
    }

    fun dispatch(action: DashboardAction) {
        when (action) {
            is DashboardAction.AddWorkout -> {
                stateLiveData.addWorkout(action.workout.distance)
                workoutRepository.add(action.workout.distance)
            }
        }
    }
}