package de.heilsen.virtuallychallenging.dashboard

data class DashboardState(val current: Float, val goal: Float) {
    class LiveData(dashboardState: DashboardState) :
        androidx.lifecycle.LiveData<DashboardState>(dashboardState) {
        fun addWorkout(distance: Float) {
            value = value?.copy(current = value?.current?.plus(distance) ?: 0.0f)
        }
    }
}