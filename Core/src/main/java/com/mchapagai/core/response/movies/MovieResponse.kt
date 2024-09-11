package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants
import com.mchapagai.core.utils.DateTimeUtils

data class MovieResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id") var movieId: Int = 0,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
) {
    fun getFullPosterPath(): String {
        return if (posterPath != null) {
            Constants.SECURE_IMAGE_ENDPOINT.plus(posterPath)
        } else {
            ""
        }
    }

    fun getFullBackdropPath(): String {
        return Constants.SECURE_IMAGE_ENDPOINT.plus(backdropPath)
    }

    fun getFormattedReleaseDate(): String {
        return DateTimeUtils.getNameOfMonth(releaseDate)
    }
}