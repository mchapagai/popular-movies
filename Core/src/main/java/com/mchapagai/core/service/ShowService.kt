package com.mchapagai.core.service

import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.shows.ShowsDetailsResponse
import com.mchapagai.core.response.shows.ShowListResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {
    @GET("discover/tv")
    fun discoverShows(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): Flowable<ShowListResponse>

    @GET("tv/{showId}?append_to_response=videos")
    fun fetchShowDetailsById(
        @Path("showId") showId: Int
    ): Observable<ShowsDetailsResponse>

    @GET("movie/{showId}/reviews")
    fun fetchShowReviewsById(
        @Path("showId") showId: Int
    ): Observable<ReviewListResponse>
}