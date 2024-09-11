package com.mchapagai.core.api

import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.shows.ShowListResponse
import com.mchapagai.core.response.shows.ShowsDetailsResponse
import com.mchapagai.core.service.ShowService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Flowable
import io.reactivex.Observable

class ShowAPIImpl(private val service: ShowService) : ShowAPI {
    override fun discoverShows(page: Int, sortBy: String): Flowable<ShowListResponse> {
        return service.discoverShows(page, sortBy)
            .compose(RxUtils.applyFlowableSchedulers())
    }

    override fun fetchShowDetailsById(showId: Int): Observable<ShowsDetailsResponse> {
        return service.fetchShowDetailsById(showId)
            .compose(RxUtils.applyObservableSchedulers())
    }

    override fun fetchShowReviewsById(showId: Int): Observable<ReviewListResponse> {
        return service.fetchShowReviewsById(showId)
            .compose(RxUtils.applyObservableSchedulers())
    }
}