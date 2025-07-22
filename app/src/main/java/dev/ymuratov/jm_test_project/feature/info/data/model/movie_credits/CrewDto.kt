package dev.ymuratov.jm_test_project.feature.info.data.model.movie_credits


import dev.ymuratov.jm_test_project.feature.info.domain.model.CrewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewDto(
    val adult: Boolean,
    @SerialName("credit_id")
    val creditId: String?,
    val department: String?,
    val gender: Int,
    val id: Int,
    val job: String?,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double?,
    @SerialName("profile_path")
    val profilePath: String?
)

fun CrewDto.toDomain() = CrewModel(
    adult = adult,
    creditId = creditId,
    department = department,
    id = id,
    job = job,
    name = name
)