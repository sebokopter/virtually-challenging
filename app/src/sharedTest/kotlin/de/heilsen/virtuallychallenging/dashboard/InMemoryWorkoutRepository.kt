package de.heilsen.virtuallychallenging.dashboard

import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object InMemoryWorkoutRepository : WorkoutRepository {
    private var workoutFlow = MutableStateFlow(emptyList<Workout>())

    override fun workouts(): Flow<List<Workout>> = workoutFlow

    override suspend fun add(workout: Workout) {
        workoutFlow.value = workoutFlow.value + workout
    }

    override suspend fun delete(workout: Workout): Int {
        val currentList = workoutFlow.value
        val removed = currentList.count { it == workout }
        workoutFlow.value = currentList.filterNot { it == workout }
        return removed
    }

    fun clear() {
        workoutFlow.value = emptyList()
    }
}