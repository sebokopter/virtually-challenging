package de.heilsen.virtuallychallenging.util

import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun <T : AppCompatDialogFragment> T.show(manager: FragmentManager) =
    show(manager, javaClass.simpleName)

fun <T : DialogFragment> T.show(manager: FragmentManager) = show(manager, javaClass.simpleName)