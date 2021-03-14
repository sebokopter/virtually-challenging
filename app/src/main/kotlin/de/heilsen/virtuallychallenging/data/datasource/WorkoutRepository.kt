package de.heilsen.virtuallychallenging.data.datasource

interface WorkoutRepository {
    fun get(): Float
    fun add(current: Float)
}