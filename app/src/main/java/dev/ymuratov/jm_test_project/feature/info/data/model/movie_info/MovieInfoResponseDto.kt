package dev.ymuratov.jm_test_project.feature.info.data.model.movie_info


import dev.ymuratov.jm_test_project.feature.info.domain.model.MovieInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.RoundingMode

@Serializable
data class MovieInfoResponseDto(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("belongs_to_collection") val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerialName("imdb_id") val imdbId: String,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>,
    @SerialName("release_date") val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

fun MovieInfoResponseDto.toDomain(): MovieInfoModel {
    return MovieInfoModel(
        adult = adult,
        backdropPath = backdropPath,
        budget = budget,
        genres = genres.map { it.name },
        id = id,
        overview = overview,
        productionCountries = productionCountries.map { it.name },
        revenue = revenue,
        title = title,
        voteAverage = voteAverage.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
    )
}