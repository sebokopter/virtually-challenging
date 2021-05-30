package de.heilsen.virtuallychallenging.domain.model

import de.heilsen.virtuallychallenging.util.sumByFloat
import java.time.LocalDateTime

data class Workout(
    val distance: Distance,
    val date: LocalDateTime = LocalDateTime.now(),
    val id: Int = 0
)


fun Iterable<Workout>.sumDistance(): Float =
    sumByFloat { workout -> workout.distance.toFloat() }