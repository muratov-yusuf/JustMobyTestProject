package dev.ymuratov.jm_test_project.feature.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.ymuratov.jm_test_project.core.data.network.utils.handle
import dev.ymuratov.jm_test_project.core.data.network.utils.remoteRequest
import dev.ymuratov.jm_test_project.feature.movies.data.model.genre.toDomain
import dev.ymuratov.jm_test_project.feature.movies.data.model.movies.toDomain
import dev.ymuratov.jm_test_project.feature.movies.data.remote_source.MoviesApiService
import dev.ymuratov.jm_test_project.feature.movies.data.remote_source.MoviesPagingSource
import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel
import dev.ymuratov.jm_test_project.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService
) : MoviesRepository {

    override suspend fun getGenres(): Result<List<GenreModel>> = remoteRequest {
        moviesApiService.getGenresList().handle { genresDto -> genresDto.genres.map { it.toDomain() } }
    }

    override suspend fun getMoviesByGenre(genreIds: List<Int>, page: Int): Result<List<MovieItemModel>> =
        remoteRequest {
            moviesApiService.getMoviesByGenre(withGenres = genreIds)
                .handle { moviesDto -> moviesDto.movies.map { it.toDomain() } }
        }

    override suspend fun subscribeToMoviesByGenre(genreIds: List<Int>): Flow<PagingData<MovieItemModel>> {
        return Pager(PagingConfig(pageSize = 20)) {
            MoviesPagingSource(genreIds, moviesApiService)
        }.flow.map { pagingData -> pagingData.map { movieItemDto -> movieItemDto.toDomain() } }
    }
}