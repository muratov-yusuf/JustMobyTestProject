package dev.ymuratov.jm_test_project.feature.info.data.model.movie_credits


import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieCreditsModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)

fun MovieCreditsDto.toDomain() = MovieCreditsModel(
    cast = cast.map { it.toDomain() },
    crew = crew.map { it.toDomain() },
    id = id
)