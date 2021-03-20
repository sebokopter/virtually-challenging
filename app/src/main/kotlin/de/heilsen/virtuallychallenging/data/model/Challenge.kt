package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.heilsen.virtuallychallenging.domain.model.Distance

@Entity
data class Challenge(
    val goal: Distance
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
