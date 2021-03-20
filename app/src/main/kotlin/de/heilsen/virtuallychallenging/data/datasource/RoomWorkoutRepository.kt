package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import java.time.Instant

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override suspend fun getAll(): Iterable<Workout> {
        return workoutDao.getAll()
    }

    override suspend fun add(distance: Float) {
        workoutDao.insert(Workout(distance, Instant.now()))
    }
}