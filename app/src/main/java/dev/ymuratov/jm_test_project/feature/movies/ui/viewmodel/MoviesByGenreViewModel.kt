package dev.ymuratov.jm_test_project.feature.movies.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ymuratov.jm_test_project.feature.movies.domain.repository.MoviesRepository
import dev.ymuratov.jm_test_project.feature.movies.ui.state.MoviesByGenreState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val repository: MoviesRepository
) : ViewModel() {

    val genreId = savedStateHandle.get<Int>("genreId") ?: -1

    private val _uiState = MutableStateFlow(MoviesByGenreState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.subscribeToMoviesByGenre(listOf(genreId)).cachedIn(viewModelScope).collectLatest { movies ->
                _uiState.update { it.copy(movies = movies) }
            }
        }
    }
}