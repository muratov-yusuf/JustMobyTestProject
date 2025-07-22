package dev.ymuratov.jm_test_project.feature.info.data.repository

import dev.ymuratov.jm_test_project.core.data.network.utils.handle
import dev.ymuratov.jm_test_project.core.data.network.utils.remoteRequest
import dev.ymuratov.jm_test_project.feature.info.data.api.MovieInfoApiService
import dev.ymuratov.jm_test_project.feature.info.data.model.movie_credits.toDomain
import dev.ymuratov.jm_test_project.feature.info.data.model.movie_info.toDomain
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieCreditsModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieInfoModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieVideoModel
import dev.ymuratov.jm_test_project.feature.info.domain.repository.MovieInfoRepository
import javax.inject.Inject

class MovieInfoRepositoryImpl @Inject constructor(
    private val apiService: MovieInfoApiService
) : MovieInfoRepository {

    override suspend fun getMovieInfo(movieId: Int): Result<MovieInfoModel> = remoteRequest {
        apiService.getMovieInfo(movieId).handle { info ->
            info.toDomain()
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Result<MovieVideoModel?> = remoteRequest {
        apiService.getMovieVideos(movieId).handle { videos ->
            val video = videos.results.firstOrNull { video -> video.type == "Teaser" && video.site == "YouTube" }
            if (video == null) null else MovieVideoModel(video.id, video.key)
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Result<MovieCreditsModel> = remoteRequest{
        apiService.getMovieCredits(movieId).handle { credits ->
            credits.toDomain()
        }
    }
}