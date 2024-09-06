package com.mchapagai.core.api

import com.mchapagai.core.response.movies.MovieListResponse
import io.reactivex.Flowable

interface MovieAPI {
    fun discoverMovies(page: Int, sortBy: String): Flowable<MovieListResponse>
}
