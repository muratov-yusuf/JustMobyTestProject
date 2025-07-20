package dev.ymuratov.jm_test_project.feature.movies.data.repository

import dev.ymuratov.jm_test_project.core.data.network.utils.handle
import dev.ymuratov.jm_test_project.core.data.network.utils.remoteRequest
import dev.ymuratov.jm_test_project.feature.movies.data.api.MoviesApiService
import dev.ymuratov.jm_test_project.feature.movies.data.model.genre.toDomain
import dev.ymuratov.jm_test_project.feature.movies.data.model.movies.toDomain
import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel
import dev.ymuratov.jm_test_project.feature.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService
) : MoviesRepository {

    override suspend fun getGenres(): Result<List<GenreModel>> = remoteRequest {
        moviesApiService.getGenresList().handle { genresDto -> genresDto.genres.map { it.toDomain() } }
    }

    override suspend fun getMoviesByGenre(genreIds: List<Int>): Result<List<MovieItemModel>> = remoteRequest {
        moviesApiService.getMoviesByGenre(withGenres = genreIds)
            .handle { moviesDto -> moviesDto.movies.map { it.toDomain() } }
    }
}