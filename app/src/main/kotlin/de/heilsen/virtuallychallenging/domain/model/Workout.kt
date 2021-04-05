package de.heilsen.virtuallychallenging.domain.model

import java.time.LocalDateTime

data class Workout(val distance: Distance, val date: LocalDateTime = LocalDateTime.now())