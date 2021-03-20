package de.heilsen.virtuallychallenging.data.model

import androidx.room.TypeConverter
import java.time.Instant

class JavaTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }
}