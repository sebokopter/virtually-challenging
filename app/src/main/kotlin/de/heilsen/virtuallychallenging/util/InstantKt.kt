package de.heilsen.virtuallychallenging.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.asLocalDate(): String {
    val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val dateString = atZone(ZoneId.systemDefault()).format(dateFormatter)
    return dateString ?: ""
}
