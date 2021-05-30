package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.heilsen.virtuallychallenging.domain.model.Distance
import java.time.Instant

@Entity(tableName = "Workout")
data class WorkoutEntity(
    val distance: Distance,
    val date: Instant,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)