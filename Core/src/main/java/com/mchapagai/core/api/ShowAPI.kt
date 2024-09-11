package com.mchapagai.core.api

import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.shows.ShowListResponse
import com.mchapagai.core.response.shows.ShowsDetailsResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface ShowAPI {
    fun discoverShows(page: Int, sortBy: String): Flowable<ShowListResponse>
    fun fetchShowDetailsById(showId: Int): Observable<ShowsDetailsResponse>
    fun fetchShowReviewsById(showId: Int): Observable<ReviewListResponse>
}