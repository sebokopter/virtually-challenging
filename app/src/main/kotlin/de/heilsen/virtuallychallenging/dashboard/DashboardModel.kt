package de.heilsen.virtuallychallenging.dashboard

data class DashboardModel(
    val distanceCovered: Float,
    val goal: Float,
    val currentStreak: Int,
    val workouts: Int
)