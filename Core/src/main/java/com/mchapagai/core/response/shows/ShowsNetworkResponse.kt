package com.mchapagai.core.response.shows

import com.google.gson.annotations.SerializedName

data class ShowsNetworkResponse(
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("origin_country") val originCountry: String?
)