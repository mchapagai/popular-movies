package com.mchapagai.core.response.people

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.utils.DateTimeUtils

data class CrewCreditResponse(
    @SerializedName("overview") val overview: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("video") val isVideo: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("department") val department: String,
    @SerializedName("job") val job: String,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("vote_count") val voteCount: Int
) {
    fun releaseDateYearOnly(): String {
        return DateTimeUtils.getYearOnly(releaseDate)
    }
}