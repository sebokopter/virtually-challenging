package de.heilsen.virtuallychallenging.util

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.intercepting.SingleActivityFactory
import kotlin.reflect.KClass

@Suppress("DEPRECATION")
fun <Activity : android.app.Activity> activityTestRule(
    aClass: KClass<Activity>,
    provideActivity: () -> Activity
): ActivityTestRule<Activity> {
    return ActivityTestRule(object : SingleActivityFactory<Activity>(aClass.java) {
        override fun create(intent: Intent): Activity {
            return provideActivity()
        }
    }, false, true)

}

@Suppress("DEPRECATION")
inline fun <reified Activity : android.app.Activity> activityTestRule(
    noinline provideActivity: () -> Activity
): ActivityTestRule<Activity> = activityTestRule(Activity::class, provideActivity)

