package de.heilsen.virtuallychallenging.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getAll(): Flow<List<Workout>>

    @Insert
    suspend fun insert(workout: Workout)
}
