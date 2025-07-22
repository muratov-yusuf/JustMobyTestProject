package dev.ymuratov.jm_test_project.feature.movies.data.model.genre


import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(val id: Int, val name: String)

fun GenreDto.toDomain() = GenreModel(id, name)