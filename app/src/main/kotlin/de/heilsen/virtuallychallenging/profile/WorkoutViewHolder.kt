package de.heilsen.virtuallychallenging.profile

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R

class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(workoutItem: WorkoutItem) {
        val resources = itemView.resources

        val workoutDate = itemView.findViewById<TextView>(R.id.workoutDate)
        val workoutDistance = itemView.findViewById<TextView>(R.id.workoutDistance)

        workoutDate.text = workoutItem.date
        workoutDistance.text =
            resources.getString(R.string.float_km, workoutItem.distance.toFloat())
    }

}
