package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName

data class ProductionCountriesResponse(
    @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
    @SerializedName("name") var name: String? = null
)
