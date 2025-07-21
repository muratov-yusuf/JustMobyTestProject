package dev.ymuratov.jm_test_project.feature.movies.ui.state

import androidx.paging.PagingData
import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel

data class MoviesByGenreState(
    val selectedGenre: GenreModel? = null,
    val movies: PagingData<MovieItemModel> = PagingData.empty()
)
