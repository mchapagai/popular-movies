package com.mchapagai.core.response.shows

import com.google.gson.annotations.SerializedName

data class ShowsSeasonResponse(
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?
)