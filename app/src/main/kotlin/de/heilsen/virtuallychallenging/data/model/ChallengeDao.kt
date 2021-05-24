package de.heilsen.virtuallychallenging.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenge LIMIT 1")
    fun get(): ChallengeEntity

    @Insert
    fun insert(challengeEntity: ChallengeEntity)
}
