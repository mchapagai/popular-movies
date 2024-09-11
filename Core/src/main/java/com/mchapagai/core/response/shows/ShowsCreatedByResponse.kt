package com.mchapagai.core.response.shows

import com.google.gson.annotations.SerializedName

data class ShowsCreatedByResponse(
    @SerializedName("gender") val gender: Int,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("id") val id: Int
)