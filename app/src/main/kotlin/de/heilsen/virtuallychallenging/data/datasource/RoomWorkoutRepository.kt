package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import kotlinx.coroutines.flow.Flow

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {

    override fun workouts(): Flow<List<Workout>> = workoutDao.getAll()

    override suspend fun add(workout: Workout) = workoutDao.insert(workout)

}