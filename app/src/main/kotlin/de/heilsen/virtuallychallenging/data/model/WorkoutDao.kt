package de.heilsen.virtuallychallenging.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    suspend fun getAll(): List<Workout>

    @Insert
    suspend fun insert(workout: Workout)
}
