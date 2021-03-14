package de.heilsen.virtuallychallenging.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.domain.model.Challenge

class SharedPreferencesChallengeProvider(
    private val sharedPref: SharedPreferences
    ) : ChallengeProvider {

    constructor(context: Context) : this(
        context.getSharedPreferences(
            context.getString(R.string.preference_challenge),
            Context.MODE_PRIVATE
        )
    )

    object PREF {
        const val GOAL = "goal"
    }


    override fun get(): Challenge {
        return Challenge(sharedPref.getFloat(PREF.GOAL, 0.0f))
    }

    override fun set(challenge: Challenge) {
        sharedPref.edit {
            putFloat(PREF.GOAL, challenge.goal)
        }
    }
}