package com.mchapagai.core.response.common

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("author") val author: String,
    @SerializedName("id") val reviewId: String,
    @SerializedName("content") val content: String,
    @SerializedName("url") val url: String
)
