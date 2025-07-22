package dev.ymuratov.jm_test_project.feature.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ymuratov.jm_test_project.feature.movies.domain.repository.MoviesRepository
import dev.ymuratov.jm_test_project.feature.movies.ui.action.MoviesAction
import dev.ymuratov.jm_test_project.feature.movies.ui.event.MoviesEvent
import dev.ymuratov.jm_test_project.feature.movies.ui.state.MoviesState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = Channel<MoviesAction>()
    val uiAction = _uiAction.receiveAsFlow()

    fun onEvent(event: MoviesEvent) {
        when (event) {
            MoviesEvent.RetryLoadData -> getGenres()
        }
    }

    init {
        getGenres()
    }

    private fun getGenres() = viewModelScope.launch {
        moviesRepository.getGenres().onSuccess { genresList ->
            _uiState.update { it.copy(genres = genresList) }
            genresList.forEach { genre ->
                val genreId = genre.id
                launch { loadMoviesByGenre(genreId) }
            }
        }.onFailure { exception ->
            _uiAction.send(MoviesAction.ShowError(exception))
        }
    }

    private suspend fun loadMoviesByGenre(genreId: Int) {
        moviesRepository.getMoviesByGenre(listOf(genreId)).onSuccess { movies ->
            _uiState.update { currentState ->
                val updatedGenres = currentState.genres.map { genre ->
                    if (genre.id == genreId) genre.copy(movies = movies) else genre
                }
                currentState.copy(genres = updatedGenres)
            }
        }.onFailure { exception ->
            _uiAction.send(MoviesAction.ShowError(exception))
        }
    }
}