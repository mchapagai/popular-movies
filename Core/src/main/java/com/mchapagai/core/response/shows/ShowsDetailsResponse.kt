package com.mchapagai.core.response.shows

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.response.common.GenresResponse
import com.mchapagai.core.response.common.VideoListResponse
import com.mchapagai.core.response.movies.ProductionCompaniesResponse

data class ShowsDetailsResponse(
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("videos") val videos: VideoListResponse?,
    @SerializedName("networks") val networks: List<ShowsNetworkResponse>?,
    @SerializedName("type") val type: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<GenresResponse>?,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("seasons") val seasons: List<ShowsSeasonResponse>?,
    @SerializedName("languages") val languages: List<String>?,
    @SerializedName("created_by") val createdBy: List<ShowsCreatedByResponse>?,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAirResponse?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompaniesResponse>?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("name") val name: String?,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: NextEpisodeToAirResponse?,
    @SerializedName("in_production") val inProduction: Boolean,
    @SerializedName("last_air_date") val lastAirDate: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("status") val status: String?
)