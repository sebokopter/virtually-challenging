package de.heilsen.virtuallychallenging.list

import android.app.Application
import androidx.lifecycle.*
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.workout.WorkoutModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutListViewModel @JvmOverloads constructor(
    application: Application,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AndroidViewModel(application) {
    private val workoutRepository: RoomWorkoutRepository by lazy {
        val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
        RoomWorkoutRepository(workoutDao)
    }

    private val workoutsLiveData: LiveData<List<WorkoutModel>> = workoutRepository.workouts()
        .map { workouts -> workouts.toModel() }
        .asLiveData(viewModelScope.coroutineContext)

    private val selectedWorkoutsLiveData: MutableLiveData<Set<Int>> = MutableLiveData()

    val model: LiveData<WorkoutListModel>

    init {
        val mediatorLiveData = MediatorLiveData<WorkoutListModel>()
        mediatorLiveData.addSource(workoutsLiveData) { workouts ->
            mediatorLiveData.value = mediatorLiveData.value?.copy(workouts = workouts)
                ?: WorkoutListModel(workouts = workouts)
        }
        mediatorLiveData.addSource(selectedWorkoutsLiveData) { ids ->
            val currentModel = mediatorLiveData.value ?: WorkoutListModel()
            val currentWorkouts = currentModel.workouts
            mediatorLiveData.value =
                currentModel.copy(workouts = currentWorkouts.map { workoutModel ->
                    workoutModel.copy(isSelected = ids?.contains(workoutModel.id) == true)
                }, isSelectionModeEnabled = ids != null)
        }

        model = mediatorLiveData
    }

    fun dispatch(action: WorkoutListAction) = viewModelScope.launch {
        when (action) {
            is WorkoutListAction.DeleteWorkout -> {
                withContext(ioDispatcher) {
                    workoutRepository.delete(action.workout.toDomain())
                }
            }
            is WorkoutListAction.ClickWorkout -> {
                if (isSelectionModeActive()) {
                    toggleSelectedItem(action.workout)
                }
            }
            is WorkoutListAction.LongClickWorkout -> {
                toggleSelectedItem(action.workout)
            }
            is WorkoutListAction.StartSelection -> {
                selectedWorkoutsLiveData.value = emptySet()
            }
            is WorkoutListAction.StopSelection -> {
                selectedWorkoutsLiveData.value = null
            }
            is WorkoutListAction.DeleteSelectedWorkouts -> {
                withContext(ioDispatcher) {
                    model.value?.workouts?.filter { it.isSelected }?.forEach {
                        workoutRepository.delete(it.toDomain())
                    }
                }
            }
        }
    }

    private fun toggleSelectedItem(workout: WorkoutModel) {
        if (workout.isSelected) {
            removeSelectedItem(workout.id)
        } else {
            addSelectedItem(workout.id)
        }
    }

    private fun addSelectedItem(id: Int) {
        selectedWorkoutsLiveData.value = (selectedWorkoutsLiveData.value ?: emptySet()) + id
    }

    private fun removeSelectedItem(id: Int) {
        val currentSet = selectedWorkoutsLiveData.value ?: emptySet()
        val newSet = currentSet - id
        when {
            currentSet.isNotEmpty() && newSet.isEmpty() -> {
                selectedWorkoutsLiveData.value = null
            }
            else -> {
                selectedWorkoutsLiveData.value = newSet
            }
        }
    }

    private fun isSelectionModeActive(): Boolean {
        return selectedWorkoutsLiveData.value != null
    }

}

private fun Iterable<Workout>.toModel() = map { WorkoutModel(it.distance, it.date, id = it.id) }