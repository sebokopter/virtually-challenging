package de.heilsen.virtuallychallenging.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.dashboard.DashboardAction

class WorkoutListActivity : AppCompatActivity() {
    private val viewModel: WorkoutListViewModel by viewModels()
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

    private fun observe(viewModel: WorkoutListViewModel) {
        viewModel.model().observe(this) {
            val list = it.workouts
            listAdapter.submitList(list)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val workout = listAdapter.currentList[position]
                viewModel.dispatch(DashboardAction.DeleteWorkout(workout))
            }
        })
        recyclerView.adapter = listAdapter
        helper.attachToRecyclerView(recyclerView)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
