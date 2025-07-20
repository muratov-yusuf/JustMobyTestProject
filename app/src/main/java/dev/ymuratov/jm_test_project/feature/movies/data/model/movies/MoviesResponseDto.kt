package dev.ymuratov.jm_test_project.feature.movies.data.model.movies


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseDto(
    val page: Int,
    @SerialName("results") val movies: List<MovieItemDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)