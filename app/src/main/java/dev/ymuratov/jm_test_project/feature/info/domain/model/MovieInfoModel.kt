package dev.ymuratov.jm_test_project.feature.info.domain.model

data class MovieInfoModel(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<String>,
    val id: Int,
    val overview: String,
    val productionCountries: List<String>,
    val revenue: Int,
    val title: String,
    val voteAverage: Double,
)
