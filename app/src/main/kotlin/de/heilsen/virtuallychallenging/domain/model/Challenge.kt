package de.heilsen.virtuallychallenging.domain.model

typealias Distance = Float

val Int.km
    get() = toFloat()
val Float.km
    get() = toFloat()
val Int.m
    get() = toFloat()/1000
val Float.m
    get() = toFloat()/1000

data class Challenge(val goal: Distance) //TODO: add start, end
