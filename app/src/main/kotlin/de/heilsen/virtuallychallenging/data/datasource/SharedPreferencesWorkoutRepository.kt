package de.heilsen.virtuallychallenging.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.domain.model.Workout

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

    override fun get(): Float {
        return sharedPref.getFloat(PREF.CURRENT, 0.0f)
    }

    override fun add(distance: Float) {
        val current = get()
        sharedPref.edit {
            putFloat(PREF.CURRENT, current + distance)
        }
    }
}