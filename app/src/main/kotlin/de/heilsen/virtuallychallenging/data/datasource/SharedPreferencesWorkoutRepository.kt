package de.heilsen.virtuallychallenging.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import de.heilsen.virtuallychallenging.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesWorkoutRepository(private val sharedPref: SharedPreferences) :
    WorkoutRepository {
    constructor(context: Context) : this(
        context.getSharedPreferences(
            context.getString(R.string.preference_workout),
            Context.MODE_PRIVATE
        )
    )

    object PREF {
        const val CURRENT = "current"
    }

    override suspend fun get(): Float = withContext(Dispatchers.IO) {
        return@withContext sharedPref.getFloat(PREF.CURRENT, 0.0f)
    }

    override suspend fun add(distance: Float) = withContext(Dispatchers.IO) {
        val current = get()
        sharedPref.edit {
            putFloat(PREF.CURRENT, current + distance)
        }
    }
}