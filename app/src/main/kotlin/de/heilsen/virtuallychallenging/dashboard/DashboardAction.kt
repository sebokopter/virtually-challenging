package de.heilsen.virtuallychallenging.dashboard

import de.heilsen.virtuallychallenging.workout.WorkoutModel

sealed class DashboardAction {
    class AddWorkout(val workout: WorkoutModel) : DashboardAction()
}