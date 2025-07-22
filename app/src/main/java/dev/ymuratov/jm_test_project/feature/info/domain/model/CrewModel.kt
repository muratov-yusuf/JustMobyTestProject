package dev.ymuratov.jm_test_project.feature.info.domain.model


data class CrewModel(
    val adult: Boolean,
    val creditId: String?,
    val department: String?,
    val id: Int,
    val job: String?,
    val name: String,
)