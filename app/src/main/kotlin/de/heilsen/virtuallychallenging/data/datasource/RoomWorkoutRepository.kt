package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override suspend fun get(): Float = withContext(Dispatchers.IO) {
        return@withContext workoutDao.getAll().sumByFloat { it.distance }

    }

    override suspend fun add(distance: Float) = withContext(Dispatchers.IO) {
        workoutDao.insert(Workout(distance))
    }
}

fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    return sumByDouble { selector(it).toDouble() }.toFloat()
}