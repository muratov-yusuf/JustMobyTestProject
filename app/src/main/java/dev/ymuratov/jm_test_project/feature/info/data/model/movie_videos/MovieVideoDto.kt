package dev.ymuratov.jm_test_project.feature.info.data.model.movie_videos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoDto(
    val id: String?,
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerialName("published_at")
    val publishedAt: String?,
    val site: String,
    val size: Int,
    val type: String
)