package dev.ymuratov.jm_test_project.feature.info.domain.model

data class MovieCreditsModel(
    val cast: List<CastModel>,
    val crew: List<CrewModel>,
    val id: Int
)