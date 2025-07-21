package dev.ymuratov.jm_test_project.feature.info.data.api

import dev.ymuratov.jm_test_project.feature.info.data.model.movie_credits.MovieCreditsDto
import dev.ymuratov.jm_test_project.feature.info.data.model.movie_info.MovieInfoResponseDto
import dev.ymuratov.jm_test_project.feature.info.data.model.movie_videos.MovieVideosResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieInfoApiService {

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieInfo(@Path(REQUEST_PATH_MOVIE_ID) movieId: Int): Response<MovieInfoResponseDto>

    @GET("movie/{movie_id}/videos?language=en-US")
    suspend fun getMovieVideos(@Path(REQUEST_PATH_MOVIE_ID) movieId: Int): Response<MovieVideosResponseDto>

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(@Path(REQUEST_PATH_MOVIE_ID) movieId: Int): Response<MovieCreditsDto>

    companion object {
        private const val REQUEST_PATH_MOVIE_ID = "movie_id"
    }
}