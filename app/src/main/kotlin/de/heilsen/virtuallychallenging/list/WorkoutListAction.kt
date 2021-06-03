package de.heilsen.virtuallychallenging.list

import de.heilsen.virtuallychallenging.workout.WorkoutModel

sealed class WorkoutListAction {
    class AddWorkout(val workout: WorkoutModel) : WorkoutListAction()
    class DeleteWorkout(val workout: WorkoutModel) : WorkoutListAction()
    class ClickWorkout(val workout: WorkoutModel) : WorkoutListAction()
    class LongClickWorkout(val workout: WorkoutModel) : WorkoutListAction()
    object StopSelection : WorkoutListAction()
    object DeleteSelectedWorkouts : WorkoutListAction()
    object StartSelection : WorkoutListAction()
}