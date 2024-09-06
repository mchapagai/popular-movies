package com.mchapagai.core.api

import com.mchapagai.core.response.movies.MovieListResponse
import com.mchapagai.core.service.MovieService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Flowable

class MoviesAPIImpl(private val service: MovieService) : MovieAPI {
    override fun discoverMovies(page: Int, sortBy: String): Flowable<MovieListResponse> {
        return service.discoverMovies(page, sortBy).compose(RxUtils.applyFlowableSchedulers())
    }
}