package dev.ymuratov.jm_test_project.feature.movies.data.api

import dev.ymuratov.jm_test_project.feature.movies.data.model.genre.GenresResponseDto
import dev.ymuratov.jm_test_project.feature.movies.data.model.movies.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("genre/movie/list?language=en")
    suspend fun getGenresList(): Response<GenresResponseDto>

    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc")
    suspend fun getMoviesByGenre(
        @Query(REQUEST_QUERY_PAGE) page: Int = 1,
        @Query(REQUEST_QUERY_WITH_GENRES) withGenres: List<Int>,
    ): Response<MoviesResponseDto>

    companion object {
        const val REQUEST_QUERY_PAGE = "page"
        const val REQUEST_QUERY_WITH_GENRES = "with_genres"
    }
}