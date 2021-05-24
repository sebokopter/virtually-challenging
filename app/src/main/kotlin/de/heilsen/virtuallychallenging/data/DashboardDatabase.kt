package de.heilsen.virtuallychallenging.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.heilsen.virtuallychallenging.data.model.ChallengeDao
import de.heilsen.virtuallychallenging.data.model.ChallengeEntity
import de.heilsen.virtuallychallenging.data.model.WorkoutDao
import de.heilsen.virtuallychallenging.data.model.WorkoutEntity

@Database(entities = [ChallengeEntity::class, WorkoutEntity::class], version = 1)
@TypeConverters(JavaTimeConverter::class, NumberConverter::class)
abstract class DashboardDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun challengeDao(): ChallengeDao

    companion object {
        operator fun invoke(context: Context) = Room.databaseBuilder(
            context,
            DashboardDatabase::class.java,
            "dashboard"
        ).build()
    }
}