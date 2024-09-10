package com.mchapagai.core.response.people

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants
import com.mchapagai.core.utils.DateTimeUtils

data class CastCreditResponse(
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("character") val character: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("video") val isVideo: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("adult") val isAdult: Boolean
) {

    fun getProfilePath(): String {
        return Constants.SECURE_IMAGE_ENDPOINT.plus(posterPath)
    }

    fun releaseDateYearOnly(): String? {
        val date = this.releaseDate ?: return null
        return DateTimeUtils.getYearOnly(date)
    }
}