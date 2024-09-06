package com.mchapagai.core.service

import com.mchapagai.core.response.shows.ShowListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowService {
    @GET("discover/tv")
    fun discoverShows(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): Flowable<ShowListResponse>
}