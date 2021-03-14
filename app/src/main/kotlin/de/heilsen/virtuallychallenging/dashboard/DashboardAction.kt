package de.heilsen.virtuallychallenging.dashboard

import de.heilsen.virtuallychallenging.domain.model.Workout

sealed class DashboardAction {
    class AddWorkout(val workout: Workout) : DashboardAction()
}