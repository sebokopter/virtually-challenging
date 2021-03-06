package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun workouts(): Flow<List<Workout>>
    suspend fun add(workout: Workout)
    suspend fun delete(workout: Workout): Int
}