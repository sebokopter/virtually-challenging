package de.heilsen.virtuallychallenging.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.list.WorkoutListActivity
import de.heilsen.virtuallychallenging.util.setProgressCompat
import de.heilsen.virtuallychallenging.util.show

class DashboardActivity(interceptingViewModelFactory: ViewModelProvider.Factory?) :
    AppCompatActivity() {

    constructor() : this(null)

    private val viewModel: DashboardViewModel by viewModels {
        interceptingViewModelFactory ?: DashboardViewModel.Factory(application)
    }

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
        viewModel.model.observe(this@DashboardActivity, render())
    }

    private fun render(): (DashboardModel) -> Unit = { state ->
        val (current, goal, consecutiveDays, workouts) = state
        with(findViewById<TextView>(R.id.progressText)) {
            text = resources.getString(R.string.float_km, current)
        }
        with(findViewById<TextView>(R.id.goalText)) {
            text = resources.getString(R.string.int_km, goal.toInt())
        }
        with(findViewById<TextView>(R.id.consecutiveDays)) {
            text = consecutiveDays.toString()
        }
        with(findViewById<TextView>(R.id.totalWorkouts)) {
            text = workouts.toString()
        }
        with(findViewById<ProgressBar>(R.id.progressIndicator)) {
            max = goal.toInt()
            setProgressCompat(current, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_profile -> {
                showProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showProfile() {
        val intent = Intent(this, WorkoutListActivity::class.java)
        startActivity(intent)
    }
}