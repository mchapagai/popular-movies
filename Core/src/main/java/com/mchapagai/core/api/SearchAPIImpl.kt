package com.mchapagai.core.api

import com.mchapagai.core.response.search.SearchResponse
import com.mchapagai.core.service.SearchService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Observable

class SearchAPIImpl(private val service: SearchService) : SearchAPI {
    override fun search(query: String): Observable<SearchResponse> {
        return service.search(query)
            .compose(RxUtils.applyObservableSchedulers())
    }
}