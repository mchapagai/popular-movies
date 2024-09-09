package com.mchapagai.core.model

import com.mchapagai.core.response.movies.MovieCastResponse
import com.mchapagai.core.response.movies.MovieCrewResponse

class MovieCombinedCreditModel(
    var creditId: Int,
    var name: String,
    var description: String,
    var profileImagePath: String? = null,
) {

    fun combinedCreditsResponse(
        cast: List<MovieCastResponse>,
        crew: List<MovieCrewResponse>
    ): List<MovieCombinedCreditModel> {
        return listOf(
            cast, crew
        ).flatMap { credits ->
            credits.map { response ->
                when (response) {
                    is MovieCastResponse -> MovieCombinedCreditModel(
                        creditId = response.id,
                        name = response.name,
                        description = response.character,
                        profileImagePath = response.profilePath
                    )

                    is MovieCrewResponse -> MovieCombinedCreditModel(
                        creditId = response.id,
                        name = response.name,
                        description = response.job,
                        profileImagePath = response.profilePath
                    )

                    else -> throw IllegalArgumentException("unknown credit type")
                }
            }
        }
    }
}