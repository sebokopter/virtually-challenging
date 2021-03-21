package de.heilsen.virtuallychallenging.util

import android.widget.EditText

inline fun EditText.doAfterFocus(crossinline action: () -> Unit) {
    setOnFocusChangeListener { _, hasFocus -> if (hasFocus) action() }
}
