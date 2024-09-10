package com.mchapagai.core.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<SearchResultResponse> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)
