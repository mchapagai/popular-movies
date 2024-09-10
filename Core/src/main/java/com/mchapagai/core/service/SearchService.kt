package com.mchapagai.core.service

import com.mchapagai.core.response.search.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/multi")
    fun search(@Query("query") query: String): Observable<SearchResponse>
}