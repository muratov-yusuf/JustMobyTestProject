package dev.ymuratov.jm_test_project.feature.movies.domain.repository

import androidx.paging.PagingData
import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getGenres(): Result<List<GenreModel>>
    suspend fun getMoviesByGenre(genreIds: List<Int>, page: Int = 1): Result<List<MovieItemModel>>
    suspend fun subscribeToMoviesByGenre(genreIds: List<Int>): Flow<PagingData<MovieItemModel>>
}