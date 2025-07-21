package dev.ymuratov.jm_test_project.feature.info.data.model.movie_videos


import kotlinx.serialization.Serializable

@Serializable
data class MovieVideosResponseDto(
    val id: Int,
    val results: List<MovieVideoDto>
)