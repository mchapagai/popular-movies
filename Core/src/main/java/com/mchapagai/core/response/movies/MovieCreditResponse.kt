package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName

data class MovieCreditResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cast") var cast: ArrayList<MovieCastResponse> = arrayListOf(),
    @SerializedName("crew") var crew: ArrayList<MovieCrewResponse> = arrayListOf()
)