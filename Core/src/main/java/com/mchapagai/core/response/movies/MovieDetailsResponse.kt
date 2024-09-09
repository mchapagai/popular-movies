package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants
import com.mchapagai.core.response.common.GenresResponse
import com.mchapagai.core.utils.DateTimeUtils

data class MovieDetailsResponse(
    @SerializedName("id") var movieId: Int = 0,
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("tagline") var tagline: String = "",
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("overview") var overview: String,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Any? = null,
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("belongs_to_collection") var belongsToCollection: Any? = null,
    @SerializedName("budget") var budget: Int? = null,
    @SerializedName("genres") var genres: ArrayList<GenresResponse> = arrayListOf(),
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("origin_country") var originCountry: ArrayList<String> = arrayListOf(),
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompaniesResponse> = arrayListOf(),
    @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountriesResponse> = arrayListOf(),
    @SerializedName("revenue") var revenue: Int? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("spoken_languages") var spokenLanguages: ArrayList<SpokenLanguagesResponse> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("video") var video: Boolean? = null
) {
    fun getFullBackdropPath() =
        if (backdropPath.isBlank()) null else Constants.SECURE_IMAGE_ENDPOINT.plus(backdropPath)

    fun getFormattedReleaseDate(): String {
        return DateTimeUtils.getNameOfMonth(releaseDate)
    }
}