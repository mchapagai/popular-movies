package com.mchapagai.core.response.shows

import com.google.gson.annotations.SerializedName

data class LastEpisodeToAirResponse(
    @SerializedName("production_code") val productionCode: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("vote_average") val voteAverage: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_count") val voteCount: Int
)