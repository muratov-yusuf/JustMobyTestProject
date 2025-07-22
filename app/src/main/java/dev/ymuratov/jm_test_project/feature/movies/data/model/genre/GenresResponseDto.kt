package dev.ymuratov.jm_test_project.feature.movies.data.model.genre


import kotlinx.serialization.Serializable

@Serializable
data class GenresResponseDto(
    val genres: List<GenreDto>
)