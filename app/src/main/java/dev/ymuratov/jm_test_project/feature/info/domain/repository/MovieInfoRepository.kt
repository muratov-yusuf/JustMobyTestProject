package dev.ymuratov.jm_test_project.feature.info.domain.repository

import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieCreditsModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieInfoModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieVideoModel

interface MovieInfoRepository {

    suspend fun getMovieInfo(movieId: Int): Result<MovieInfoModel>
    suspend fun getMovieVideos(movieId: Int): Result<MovieVideoModel>
    suspend fun getMovieCredits(movieId: Int): Result<MovieCreditsModel>
}