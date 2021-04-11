package de.heilsen.virtuallychallenging.dashboard

data class DashboardModel(
    val distanceCovered: Float,
    val goal: Float,
    val consecutiveDays: Int,
    val workouts: Int
)