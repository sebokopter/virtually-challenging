package de.heilsen.virtuallychallenging.domain.model

import java.time.Instant

data class Workout(val distance: Distance, val date: Instant = Instant.now())