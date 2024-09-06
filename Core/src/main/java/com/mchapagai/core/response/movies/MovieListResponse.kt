package com.mchapagai.core.response.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListResponse(
    @SerializedName("page") var page: Int = 0,
    @SerializedName("results") var movies: ArrayList<MovieResponse> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("total_results") var totalResults: Int = 0
): Parcelable