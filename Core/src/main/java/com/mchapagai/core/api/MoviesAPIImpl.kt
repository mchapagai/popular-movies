package com.mchapagai.core.api

import com.mchapagai.core.response.movies.MovieDetailsResponse
import com.mchapagai.core.response.movies.MovieListResponse
import com.mchapagai.core.service.MovieService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Flowable
import io.reactivex.Observable

class MoviesAPIImpl(private val service: MovieService) : MovieAPI {
    override fun discoverMovies(page: Int, sortBy: String): Flowable<MovieListResponse> {
        return service.discoverMovies(page, sortBy).compose(RxUtils.applyFlowableSchedulers())
    }

    override fun fetchMovieDetailsByMovieId(movieId: Int): Observable<MovieDetailsResponse> {
        return service.fetchMovieDetailsByMovieId(movieId)
            .compose(RxUtils.applyObservableSchedulers())
    }
}