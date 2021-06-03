package de.heilsen.virtuallychallenging.workout

import de.heilsen.virtuallychallenging.domain.model.Distance
import de.heilsen.virtuallychallenging.domain.model.Workout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class WorkoutModel(
    val distance: Distance,
    private val dateTime: LocalDateTime,
    var isSelected: Boolean = false,
    val id: Int = 0
) {

    val date: String = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(dateTime)

    fun toDomain() = Workout(distance, dateTime, id)
}