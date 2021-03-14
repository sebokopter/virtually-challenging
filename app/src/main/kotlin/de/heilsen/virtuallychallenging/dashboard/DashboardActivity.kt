package de.heilsen.virtuallychallenging.dashboard

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.domain.model.km
import de.heilsen.virtuallychallenging.util.sumBy

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.challenge_activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar))

        val viewModel: DashboardViewModel by viewModels()
        observe(viewModel)
        dispatchTo(viewModel)
    }

    private fun dispatchTo(viewModel: DashboardViewModel) {
        findViewById<MaterialButton>(R.id.add_training_fab).setOnClickListener {
            viewModel.dispatch(DashboardAction.AddWorkout(Workout(1.km)))
        }
    }

    private fun observe(viewModel: DashboardViewModel) {
        viewModel.stateLiveData.observe(this@DashboardActivity, render())
    }

    private fun render(): (DashboardState) -> Unit = { state ->
        val (current, goal) = state
        with(findViewById<TextView>(R.id.progressText)) {
            text = resources.getString(
                R.string.progress_text,
                current,
                goal
            )
        }
        with(findViewById<ProgressBar>(R.id.progressIndicator)) {
            progress = current.toInt()
            max = goal.toInt()
        }
    }
}