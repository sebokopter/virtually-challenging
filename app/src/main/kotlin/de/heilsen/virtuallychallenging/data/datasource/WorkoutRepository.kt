package de.heilsen.virtuallychallenging.data.datasource

interface WorkoutRepository {
    suspend fun get(): Float
    suspend fun add(distance: Float)
}