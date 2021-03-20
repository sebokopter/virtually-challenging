package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Workout(
    val distance: Float,
    val date: Instant
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}