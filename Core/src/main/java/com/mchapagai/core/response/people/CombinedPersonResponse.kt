package com.mchapagai.core.response.people

import com.google.gson.annotations.SerializedName

data class CombinedPersonResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("cast") val cast: List<CastCreditResponse> = listOf(),
    @SerializedName("crew") val crew: List<CrewCreditResponse> = listOf()
)