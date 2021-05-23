package de.heilsen.virtuallychallenging.test

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import de.heilsen.virtuallychallenging.R

class DashboardScreen : Screen<DashboardScreen>() {
    val goalText = KTextView { withId(R.id.goalText) }
    val progressText = KTextView { withId(R.id.progressText) }
    val streakText = KTextView { withId(R.id.consecutiveDays) }
    val totalWorkoutsText = KTextView { withId(R.id.totalWorkouts) }
}