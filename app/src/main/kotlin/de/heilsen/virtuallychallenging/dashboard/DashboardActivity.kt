package de.heilsen.virtuallychallenging.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.data.DashboardDatabase
import de.heilsen.virtuallychallenging.data.datasource.RoomWorkoutRepository
import de.heilsen.virtuallychallenging.profile.ProfileActivity
import de.heilsen.virtuallychallenging.util.setProgressCompat
import de.heilsen.virtuallychallenging.util.show

class DashboardActivity(viewModelFactoryProducer: (ViewModelProvider.Factory)?) :
    AppCompatActivity() {

    constructor() : this(null)

    @Suppress("UNCHECKED_CAST")
    private val defaultViewModelFactory: ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val workoutDao = DashboardDatabase(application.applicationContext).workoutDao()
                val workoutRepository = RoomWorkoutRepository(workoutDao)
                return DashboardViewModel(workoutRepository) as T
            }
        }

    private val viewModel: DashboardViewModel by viewModels {
        viewModelFactoryProducer ?: defaultViewModelFactory
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
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}