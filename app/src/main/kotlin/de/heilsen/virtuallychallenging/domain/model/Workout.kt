package de.heilsen.virtuallychallenging.domain.model

import de.heilsen.virtuallychallenging.util.sumByFloat
import java.time.LocalDateTime

data class Workout(val distance: Distance, val date: LocalDateTime = LocalDateTime.now())


fun List<Workout>.sumDistance(): Float =
    sumByFloat { workout -> workout.distance.toFloat() }