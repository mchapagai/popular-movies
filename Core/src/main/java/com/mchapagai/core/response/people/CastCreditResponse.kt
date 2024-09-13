package com.mchapagai.core.response.people

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants
import com.mchapagai.core.utils.DateTimeUtils

data class CastCreditResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("character") var character: String? = null,
    @SerializedName("credit_id") var creditId: String? = null,
    @SerializedName("order") var order: Int? = null,
    @SerializedName("media_type") var mediaType: String? = null
) {

    fun getProfilePath(): String {
        return Constants.SECURE_IMAGE_ENDPOINT.plus(posterPath)
    }

    fun releaseDateYearOnly(): String? {
        val date = this.releaseDate ?: return null
        return DateTimeUtils.getYearOnly(date)
    }
}