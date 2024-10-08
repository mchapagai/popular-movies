package com.mchapagai.core.service

import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.common.VideoListResponse
import com.mchapagai.core.response.movies.MovieCreditResponse
import com.mchapagai.core.response.movies.MovieDetailsResponse
import com.mchapagai.core.response.movies.MovieListResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): Flowable<MovieListResponse>

    // append: credits,videos,videos,reviews,similar,external_id,images
    // @GET("tv/{tvId}?append_to_response=videos")
    @GET("movie/{movieId}")
    fun fetchMovieDetailsByMovieId(
        @Path("movieId") movieId: Int
    ): Observable<MovieDetailsResponse>

    @GET("movie/{movieId}/credits")
    fun getMovieCreditDetailsByCreditId(
        @Path("movieId") movieId: Int
    ): Observable<MovieCreditResponse>

    @GET("movie/{movieId}/videos")
    fun getMovieVideosById(
        @Path("movieId") movieId: Int
    ): Observable<VideoListResponse>

    @GET("movie/{movieId}/reviews")
    fun getMovieReviewsById(
        @Path("movieId") movieId: Int
    ): Observable<ReviewListResponse>
}
