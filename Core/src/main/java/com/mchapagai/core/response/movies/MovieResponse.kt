package com.mchapagai.core.response.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants
import com.mchapagai.core.utils.DateTimeUtils
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id") var id: Int = 0,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
) : Parcelable {
    fun getFullPosterPath() =
        if (posterPath.isBlank()) null else Constants.SECURE_IMAGE_ENDPOINT.plus(posterPath)

    fun getFullBackdropPath(): String {
        return Constants.SECURE_IMAGE_ENDPOINT.plus(backdropPath)
    }

    fun getFormattedReleaseDate(): String {
        return DateTimeUtils.getNameOfMonth(releaseDate)
    }
}