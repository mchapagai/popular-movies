package com.mchapagai.core.response.common

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.common.Constants

data class VideoResponse(
    @SerializedName("site") val site: String,
    @SerializedName("size") val id: Int,
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val videoId: String,
    @SerializedName("type") val type: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("key") val key: String
) {
    fun isYoutubeVideo(): Boolean {
        return site.lowercase() == "YouTube".lowercase()
    }

    fun thumbnailUrl(): String {
        return String.format(Constants.YOUTUBE_THUMBNAIL, key)
    }
}
