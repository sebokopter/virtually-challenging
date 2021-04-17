package de.heilsen.virtuallychallenging.data

import androidx.room.TypeConverter

class NumberConverter {
    @TypeConverter
    fun fromNumber(value: Number): Float {
        return value.toFloat()
    }

    @TypeConverter
    fun toNumber(value: Float): Number {
        return value
    }

}