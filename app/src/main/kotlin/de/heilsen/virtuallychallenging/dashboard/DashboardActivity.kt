package de.heilsen.virtuallychallenging.dashboard

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.util.setProgressCompat
import de.heilsen.virtuallychallenging.util.show
import de.heilsen.virtuallychallenging.workoutform.WorkoutFormFragment

class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar))

        observe(viewModel)
        setupDispatchers()
    }

    private fun setupDispatchers() {
        findViewById<MaterialButton>(R.id.add_training_fab).setOnClickListener {
            WorkoutFormFragment().show(supportFragmentManager)
        }
    }

    private fun observe(viewModel: DashboardViewModel) {
        viewModel.model().observe(this@DashboardActivity, render())
    }

    private fun render(): (DashboardModel) -> Unit = { state ->
        val (current, goal, consecutiveDays) = state
        with(findViewById<TextView>(R.id.progressText)) {
            text = resources.getString(
                R.string.progress_text,
                current,
                goal
            )
        }
        with(findViewById<TextView>(R.id.consecutiveDays)) {
            text = consecutiveDays.toString()
        }
        with(findViewById<ProgressBar>(R.id.progressIndicator)) {
            max = goal.toInt()
            setProgressCompat(current, true)
        }
    }
}