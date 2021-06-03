package de.heilsen.virtuallychallenging.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SimpleTouchHelper(swipeCallback: (position: Int) -> Unit) :
    ItemTouchHelper(object : SimpleCallback(
        0,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            swipeCallback(position)
        }
    })

fun RecyclerView.onSwipe(swipeCallback: (position: Int) -> Unit) {
    val helper = SimpleTouchHelper { position ->
        swipeCallback(position)
    }
    helper.attachToRecyclerView(this)
}