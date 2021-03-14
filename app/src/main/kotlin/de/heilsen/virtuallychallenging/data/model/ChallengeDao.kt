package de.heilsen.virtuallychallenging.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenge LIMIT 1")
    fun get(): Challenge

    @Insert
    fun insert(challenge: Challenge)
}
