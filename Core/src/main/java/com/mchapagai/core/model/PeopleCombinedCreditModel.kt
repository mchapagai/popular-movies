package com.mchapagai.core.model

import com.mchapagai.core.common.Constants
import com.mchapagai.core.response.people.CastCreditResponse
import com.mchapagai.core.response.people.CrewCreditResponse
import com.mchapagai.core.utils.DateTimeUtils

data class PeopleCombinedCreditModel(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: ArrayList<Int> = arrayListOf(),
    val id: Int? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val character: String? = null, // From CastCreditResponse
    val order: Int? = null, // From CastCreditResponse
    val creditId: String? = null,
    val department: String? = null, // From CrewCreditResponse
    val job: String? = null, // From CrewCreditResponse
    val mediaType: String? = null
) {
    fun getProfilePath(): String {
        return Constants.SECURE_IMAGE_ENDPOINT.plus(posterPath)
    }

    fun releaseDateYearOnly(): String? {
        val date = this.releaseDate ?: return null
        return DateTimeUtils.getYearOnly(date)
    }

    fun personCombinedResponse(
        cast: List<CastCreditResponse>,
        crew: List<CrewCreditResponse>
    ): List<PeopleCombinedCreditModel> {
        return listOf(
            cast, crew
        ).flatMap { credits ->
            credits.map { model ->
                when (model) {
                    is CastCreditResponse -> PeopleCombinedCreditModel(
                        adult = model.adult,
                        backdropPath = model.backdropPath,
                        genreIds = model.genreIds,
                        id = model.id,
                        originalLanguage = model.originalLanguage,
                        originalTitle = model.originalTitle,
                        overview = model.overview,
                        popularity = model.popularity,
                        posterPath = model.posterPath,
                        releaseDate = model.releaseDate,
                        title = model.title,
                        video = model.video,
                        voteAverage = model.voteAverage,
                        voteCount = model.voteCount,
                        character = model.character,
                        order = model.order,
                        creditId = model.creditId,
                        department = "",
                        job = "",
                        mediaType = model.mediaType
                    )

                    is CrewCreditResponse -> PeopleCombinedCreditModel(
                        adult = model.adult,
                        backdropPath = model.backdropPath,
                        genreIds = model.genreIds,
                        id = model.id,
                        originalLanguage = model.originalLanguage,
                        originalTitle = model.originalTitle,
                        overview = model.overview,
                        popularity = model.popularity,
                        posterPath = model.posterPath,
                        releaseDate = model.releaseDate,
                        title = model.title,
                        video = model.video,
                        voteAverage = model.voteAverage,
                        voteCount = model.voteCount,
                        character = "",
                        order = 0,
                        creditId = model.creditId,
                        department = model.department,
                        job = model.job,
                        mediaType = model.mediaType
                    )

                    else -> throw IllegalArgumentException("unknown credit type")
                }
            }
        }
    }
}
