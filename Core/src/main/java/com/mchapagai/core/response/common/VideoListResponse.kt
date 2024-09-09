package com.mchapagai.core.response.common

import com.google.gson.annotations.SerializedName

data class VideoListResponse(
    @SerializedName("id") var videoId: Int,
    @SerializedName("results") var videoList: List<VideoResponse>,
)
