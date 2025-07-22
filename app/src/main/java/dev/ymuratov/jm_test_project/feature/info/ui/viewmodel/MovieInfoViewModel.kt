package dev.ymuratov.jm_test_project.feature.info.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ymuratov.jm_test_project.feature.info.domain.repository.MovieInfoRepository
import dev.ymuratov.jm_test_project.feature.info.ui.action.MovieInfoAction
import dev.ymuratov.jm_test_project.feature.info.ui.event.MovieInfoEvent
import dev.ymuratov.jm_test_project.feature.info.ui.state.MovieInfoState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val movieInfoRepository: MovieInfoRepository
) : ViewModel() {

    private val movieId: Int = savedStateHandle["movieId"] ?: 0

    private val _uiState = MutableStateFlow(MovieInfoState())
    val uiState get() = _uiState.asStateFlow()

    private val _uiAction = Channel<MovieInfoAction>()
    val uiAction get() = _uiAction.receiveAsFlow()

    fun onEvent(event: MovieInfoEvent) {
        when (event) {
            MovieInfoEvent.RetryLoadData -> initMovieInfoLoading()
        }
    }

    init {
        initMovieInfoLoading()
    }

    private fun initMovieInfoLoading() = viewModelScope.launch {
        movieInfoRepository.getMovieInfo(movieId).onSuccess { movieInfo ->
            _uiState.update { it.copy(movieInfo = movieInfo) }
            getCredits()
        }.onFailure {
            _uiAction.send(MovieInfoAction.ShowError(exception = it))
        }
        movieInfoRepository.getMovieVideos(movieId).onSuccess { videoModel ->
            _uiState.update { it.copy(videoId = videoModel?.videoId) }
        }.onFailure {
            _uiAction.send(MovieInfoAction.ShowError(exception = it))
        }
    }

    private fun getCredits() = viewModelScope.launch {
        movieInfoRepository.getMovieCredits(movieId).onSuccess { credits ->
            _uiState.update { it.copy(cast = credits.cast, crew = credits.crew) }
        }.onFailure {
            _uiAction.send(MovieInfoAction.ShowError(exception = it))
        }
    }
}