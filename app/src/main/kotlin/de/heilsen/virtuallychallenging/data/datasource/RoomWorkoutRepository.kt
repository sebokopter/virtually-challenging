package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import de.heilsen.virtuallychallenging.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {

    override fun workouts(): Flow<List<Workout>> = workoutDao.getAll().map { it ->
        it.map {
            Workout(
                it.distance,
                it.date.atZone(ZoneId.systemDefault()).toLocalDateTime()
            )
        }
    }

    override suspend fun add(workout: Workout) {
        workoutDao.insert(
            de.heilsen.virtuallychallenging.data.model.WorkoutEntity(
                workout.distance,
                workout.date.atZone(ZoneId.systemDefault()).toInstant()
            )
        )
    }

}