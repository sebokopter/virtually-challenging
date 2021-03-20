package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    val distance: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}