package com.mchapagai.core.response.search

import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("title") var title: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("media_type") var mediaType: String? = null,
//    @SerializedName("genre_ids") var genreIds: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
)
