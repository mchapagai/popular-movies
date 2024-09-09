package com.mchapagai.core.response.common

import com.google.gson.annotations.SerializedName

class ReviewListResponse (
    @SerializedName("id") var reviewId: Int,
    @SerializedName("results") var reviewList: List<ReviewResponse>
)