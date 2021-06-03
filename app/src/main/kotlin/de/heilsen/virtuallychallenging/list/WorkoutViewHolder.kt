package de.heilsen.virtuallychallenging.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.workout.WorkoutModel

class WorkoutViewHolder(
    itemView: View,
    private val onClick: (WorkoutModel) -> Unit,
    private val onLongClick: (WorkoutModel) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(workoutItem: WorkoutModel) {
        val resources = itemView.resources

        val workoutDate = itemView.findViewById<TextView>(R.id.workoutDate)
        val workoutDistance = itemView.findViewById<TextView>(R.id.workoutDistance)

        workoutDate.text = workoutItem.date
        workoutDistance.text =
            resources.getString(R.string.float_km, workoutItem.distance.toFloat())

        itemView.isActivated = workoutItem.isSelected

        itemView.setOnClickListener {
            onClick(workoutItem)
        }
        itemView.setOnLongClickListener {
            onLongClick(workoutItem)
            true
        }
    }

}
