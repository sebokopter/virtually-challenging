package de.heilsen.virtuallychallenging.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getAll(): Flow<List<WorkoutEntity>>

    @Insert
    suspend fun insert(workoutEntity: WorkoutEntity)

    @Delete
    fun delete(workoutEntity: WorkoutEntity): Int
}
