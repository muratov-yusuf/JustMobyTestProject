package dev.ymuratov.jm_test_project.feature.info.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle["movieId"] ?: 0

    init {
        Timber.d(movieId.toString())
    }
}