package dev.ymuratov.jm_test_project.feature.movies.domain.model

data class GenreModel(
    val id: Int,
    val name: String,
    val movies: List<MovieItemModel> = emptyList()
)