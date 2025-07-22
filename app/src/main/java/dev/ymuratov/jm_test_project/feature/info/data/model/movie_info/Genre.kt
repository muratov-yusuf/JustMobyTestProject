package dev.ymuratov.jm_test_project.feature.info.data.model.movie_info


import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)