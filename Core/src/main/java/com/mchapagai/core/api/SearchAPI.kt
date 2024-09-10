package com.mchapagai.core.api

import com.mchapagai.core.response.search.SearchResponse
import io.reactivex.Observable

interface SearchAPI {
    fun search(query: String): Observable<SearchResponse>
}