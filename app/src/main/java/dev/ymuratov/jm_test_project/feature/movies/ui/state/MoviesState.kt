package dev.ymuratov.jm_test_project.feature.movies.ui.state

import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel

data class MoviesState(
    val isLoading: Boolean = false,
    val genres: List<GenreModel> = emptyList(),
)
