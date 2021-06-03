package de.heilsen.virtuallychallenging.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.workout.WorkoutModel

class WorkoutListAdapter(
    private val onItemClick: (WorkoutModel) -> Unit,
    private val onItemLongClick: (WorkoutModel) -> Unit
) :
    ListAdapter<WorkoutModel, WorkoutViewHolder>(WorkoutItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workoutItem = currentList[position]
        holder.bind(workoutItem)
    }

    object WorkoutItemCallback : DiffUtil.ItemCallback<WorkoutModel>() {
        override fun areItemsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel): Boolean {
            val sameDate = oldItem.date == newItem.date
            val sameDistance = oldItem.distance.toFloat() == newItem.distance.toFloat()
            return sameDate && sameDistance
        }
    }
}
