package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import de.heilsen.virtuallychallenging.data.model.WorkoutEntity
import de.heilsen.virtuallychallenging.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId

class RoomWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {

    override fun workouts(): Flow<List<Workout>> = workoutDao.getAll().map { entities ->
        entities.map {
            Workout(
                it.distance,
                it.date.atZone(ZoneId.systemDefault()).toLocalDateTime(),
                it.id
            )
        }
    }

    override suspend fun add(workout: Workout) {
        workoutDao.insert(
            WorkoutEntity(
                workout.distance,
                workout.date.atZone(ZoneId.systemDefault()).toInstant()
            )
        )
    }

    override suspend fun delete(workout: Workout): Int {
        return workoutDao.delete(
            WorkoutEntity(
                workout.distance,
                workout.date.atZone(ZoneId.systemDefault()).toInstant(),
                workout.id
            )
        )
    }

}