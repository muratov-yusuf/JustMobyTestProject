package dev.ymuratov.jm_test_project.feature.info.data.model.movie_info


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollection(
    @SerialName("backdrop_path")
    val backdropPath: String,
    val id: Int,
    val name: String,
)