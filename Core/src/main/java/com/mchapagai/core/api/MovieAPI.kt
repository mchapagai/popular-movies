package com.mchapagai.core.api

import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.common.VideoListResponse
import com.mchapagai.core.response.movies.MovieCreditResponse
import com.mchapagai.core.response.movies.MovieDetailsResponse
import com.mchapagai.core.response.movies.MovieListResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieAPI {
    fun discoverMovies(page: Int, sortBy: String): Flowable<MovieListResponse>
    fun fetchMovieDetailsByMovieId(movieId: Int): Observable<MovieDetailsResponse>
    fun getMovieCreditDetailsByCreditId(movieId: Int): Observable<MovieCreditResponse>
    fun getMovieVideosById(movieId: Int): Observable<VideoListResponse>
    fun getMovieReviewsById(movieId: Int): Observable<ReviewListResponse>
}
