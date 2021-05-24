package de.heilsen.virtuallychallenging.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM : ViewModel> viewModelFactory(viewModel: VM) =
    ViewModelFactory { viewModel }

open class ViewModelFactory<T : ViewModel>(private val viewModelProvider: () -> T) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider() as T
    }
}
