package com.mchapagai.core.api

import com.mchapagai.core.response.shows.ShowListResponse
import io.reactivex.Flowable

interface ShowAPI {
    fun discoverShows(page: Int, sortBy: String): Flowable<ShowListResponse>
}