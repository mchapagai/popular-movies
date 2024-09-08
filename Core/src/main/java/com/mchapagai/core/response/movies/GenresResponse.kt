package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
)
