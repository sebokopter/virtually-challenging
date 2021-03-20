package de.heilsen.virtuallychallenging.util

import android.os.Build
import android.widget.ProgressBar

/**
 * ProgressBar only offers animating the progress from API Level 24 on.
 */
fun ProgressBar.setProgressCompat(current: Float, animate: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setProgress(current.toInt(), animate)
    } else {
        progress = current.toInt()
    }
}
