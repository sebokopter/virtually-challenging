package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun workouts(): Flow<List<Workout>>
    suspend fun add(workout: Workout)
}