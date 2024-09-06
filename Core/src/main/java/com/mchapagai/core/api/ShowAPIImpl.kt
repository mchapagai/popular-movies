package com.mchapagai.core.api

import com.mchapagai.core.response.shows.ShowListResponse
import com.mchapagai.core.service.ShowService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Flowable

class ShowAPIImpl(private val service: ShowService) : ShowAPI {
    override fun discoverShows(page: Int, sortBy: String): Flowable<ShowListResponse> {
        return service.discoverShows(page, sortBy)
            .compose(RxUtils.applyFlowableSchedulers())
    }
}