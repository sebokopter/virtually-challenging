package de.heilsen.virtuallychallenging.data

import androidx.room.TypeConverter
import java.time.Instant

class JavaTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: String): Instant {
        return Instant.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant): String {
        return date.toString()
    }
}