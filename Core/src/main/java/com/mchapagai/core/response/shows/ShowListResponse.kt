package com.mchapagai.core.response.shows

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowListResponse(
    @SerializedName("page") var page: Int = 0,
    @SerializedName("results") var shows: ArrayList<ShowResponse> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
): Parcelable