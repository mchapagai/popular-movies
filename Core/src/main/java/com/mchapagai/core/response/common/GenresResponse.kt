package com.mchapagai.core.response.common

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("name") var genreName: String,
    @SerializedName("id") var genreId: Int
)
