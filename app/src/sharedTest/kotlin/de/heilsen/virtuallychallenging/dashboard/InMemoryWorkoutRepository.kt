package de.heilsen.virtuallychallenging.dashboard

import de.heilsen.virtuallychallenging.data.datasource.WorkoutRepository
import de.heilsen.virtuallychallenging.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object InMemoryWorkoutRepository : WorkoutRepository {
    var workoutFlow = MutableStateFlow(emptyList<Workout>())

    override fun workouts(): Flow<List<Workout>> = workoutFlow

    override suspend fun add(workout: Workout) {
        workoutFlow.value = workoutFlow.value + workout
    }

    fun clear() {
        workoutFlow.value = emptyList()
    }
}