package de.heilsen.virtuallychallenging.list

import de.heilsen.virtuallychallenging.workout.WorkoutModel

data class WorkoutListModel(
    val workouts: List<WorkoutModel> = emptyList(),
    val isSelectionModeEnabled: Boolean = false
)