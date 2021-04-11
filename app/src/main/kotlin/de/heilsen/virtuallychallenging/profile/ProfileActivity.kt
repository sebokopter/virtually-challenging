package de.heilsen.virtuallychallenging.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ProfileActivity : AppCompatActivity() {
    private val viewModel: ProfileViewModel by viewModels()
    private val listAdapter = WorkoutListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBar()
        setupRecyclerView()

        observe(viewModel)
        //TODO: setupDispatchers()
    }

    private fun observe(viewModel: ProfileViewModel) {
        viewModel.model().observe(this) {
            val list = it.workouts.map { workout ->
                val localDateString =
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(workout.date)
                WorkoutItem(workout.distance, localDateString)
            }
            listAdapter.submitList(list)
        }
    }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.recyclerView).adapter = listAdapter
    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
