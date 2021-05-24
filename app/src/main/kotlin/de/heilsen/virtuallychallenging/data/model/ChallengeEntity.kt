package de.heilsen.virtuallychallenging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.heilsen.virtuallychallenging.domain.model.Distance

@Entity(tableName = "Challenge")
data class ChallengeEntity(
    val goal: Distance
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
