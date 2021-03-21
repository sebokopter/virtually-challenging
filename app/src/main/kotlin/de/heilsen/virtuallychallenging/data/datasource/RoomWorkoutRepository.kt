package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.dashboard.DateProcessor
import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import de.heilsen.virtuallychallenging.util.sumByFloat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {

    override fun workouts(): Flow<List<Workout>> = workoutDao.getAll()

    override suspend fun add(workout: Workout) = workoutDao.insert(workout)

    override fun totalDistance(): Flow<Float> {
        return workouts().map { it.sumDistance() }
    }

    private fun List<Workout>.sumDistance(): Float = sumByFloat { workout -> workout.distance }

    override fun consecutiveDays(): Flow<Int> {
        return workouts().map { list ->
            val instants = list.map { workout -> workout.date }
            return@map DateProcessor.consecutiveDays(instants)
        }
    }
}