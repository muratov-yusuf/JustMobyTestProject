package dev.ymuratov.jm_test_project.feature.movies.data.remote_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.ymuratov.jm_test_project.core.data.network.exception.ServerException
import dev.ymuratov.jm_test_project.feature.movies.data.model.movies.MovieItemDto
import kotlinx.coroutines.delay

class MoviesPagingSource(
    private val genres: List<Int>, private val moviesApiService: MoviesApiService
) : PagingSource<Int, MovieItemDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItemDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemDto> {
        return try {
            val page = params.key ?: 1
            val response = moviesApiService.getMoviesByGenre(genres, page)
            if (response.isSuccessful) {
                val movies = response.body()?.movies ?: emptyList()
                val nextKey = if (movies.isEmpty()) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                delay(3000)
                LoadResult.Page(movies, prevKey, nextKey)
            } else {
                LoadResult.Error(ServerException(response.code()))
            }
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}