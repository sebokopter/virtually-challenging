package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val distance: Float
)