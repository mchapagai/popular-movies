package com.mchapagai.core.service

import com.mchapagai.core.response.movies.MovieListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): Flowable<MovieListResponse>
}