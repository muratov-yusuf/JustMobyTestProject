package dev.ymuratov.jm_test_project.feature.info.ui.state

import dev.ymuratov.jm_test_project.feature.info.domain.model.CastModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.CrewModel
import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieInfoModel

data class MovieInfoState(
    val movieInfo: MovieInfoModel? = null,
    val videoId: String? = null,
    val cast: List<CastModel> = emptyList(),
    val crew: List<CrewModel> = emptyList(),
)
