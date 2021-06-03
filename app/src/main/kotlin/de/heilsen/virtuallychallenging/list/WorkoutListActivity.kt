package de.heilsen.virtuallychallenging.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import de.heilsen.virtuallychallenging.R

class WorkoutListActivity : AppCompatActivity() {
    private var actionMode: ActionMode? = null
    private val viewModel: WorkoutListViewModel by viewModels()
    private val listAdapter = WorkoutListAdapter({ workout ->
        viewModel.dispatch(WorkoutListAction.ClickWorkout(workout))
    }, { workout ->
        viewModel.dispatch(WorkoutListAction.LongClickWorkout(workout))
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupActionBar()
        stopSupportActionMode()
        setupRecyclerView()

        observe(viewModel)
    }

    private fun observe(viewModel: WorkoutListViewModel) {
        viewModel.model.observe(this) { model ->
            listAdapter.submitList(model.workouts)
            if (model.isSelectionModeEnabled) {
                startSupportActionMode()
            } else {
                stopSupportActionMode()
            }
        }
    }

    private fun startSupportActionMode() {
        if (actionMode == null) {
            actionMode = startSupportActionMode(WorkoutListActionModeCallback(this, viewModel))
        }
    }

    private fun stopSupportActionMode() {
        if (actionMode != null) {
            actionMode?.finish()
            actionMode = null
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = listAdapter
        recyclerView.onSwipe { position ->
            val workout = listAdapter.currentList[position]
            viewModel.dispatch(WorkoutListAction.DeleteWorkout(workout))
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_select -> {
                viewModel.dispatch(WorkoutListAction.StartSelection)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}