package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.Workout

interface WorkoutRepository {
    suspend fun getAll(): Iterable<Workout>
    suspend fun add(distance: Float)
}