package dev.ymuratov.jm_test_project.feature.movies.domain.repository

import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel

interface MoviesRepository {

    suspend fun getGenres(): Result<List<GenreModel>>
    suspend fun getMoviesByGenre(genreIds: List<Int>): Result<List<MovieItemModel>>
}