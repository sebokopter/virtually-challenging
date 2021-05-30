package de.heilsen.virtuallychallenging.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.domain.model.Workout
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class WorkoutViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(workoutItem: Workout) {
        val resources = itemView.resources

        val workoutDate = itemView.findViewById<TextView>(R.id.workoutDate)
        val workoutDistance = itemView.findViewById<TextView>(R.id.workoutDistance)

        workoutDate.text =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(workoutItem.date)
        workoutDistance.text =
            resources.getString(R.string.float_km, workoutItem.distance.toFloat())
    }

}
