package de.heilsen.virtuallychallenging.list

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import de.heilsen.virtuallychallenging.R

class WorkoutListActionModeCallback(
    private val context: Context,
    private val viewModel: WorkoutListViewModel
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.list_action_menu, menu)
        mode.title = context.resources.getString(R.string.action_mode_list)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = false

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        viewModel.dispatch(WorkoutListAction.DeleteSelectedWorkouts)
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        viewModel.dispatch(WorkoutListAction.StopSelection)
    }

}