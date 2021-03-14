package de.heilsen.virtuallychallenging.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.heilsen.virtuallychallenging.data.model.Challenge
import de.heilsen.virtuallychallenging.data.model.ChallengeDao
import de.heilsen.virtuallychallenging.data.model.Workout
import de.heilsen.virtuallychallenging.data.model.WorkoutDao

@Database(entities = [Challenge::class, Workout::class], version = 1)
abstract class DashboardDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun ChallengeDao(): ChallengeDao

}