package de.heilsen.compat.edittext

import android.widget.EditText

object EditTextCompat {

    fun EditText.fixDigitsKeyListenerLocale() {
        keyListener = LocalDigitsKeyListener.localDigitsKeyListener(context, decimal = true)
    }

}
