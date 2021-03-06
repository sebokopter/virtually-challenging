package de.heilsen.virtuallychallenging.util

inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0.0F
    for (element in this) {
        sum += selector(element)
    }
    return sum
}