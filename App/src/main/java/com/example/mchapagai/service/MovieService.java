package com.example.mchapagai.service;

import com.example.mchapagai.model.binding.MovieDetailsResponse;
import com.example.mchapagai.model.binding.MovieResponse;
import com.example.mchapagai.model.binding.ReviewsResponse;
import com.example.mchapagai.model.binding.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie")
    Observable<MovieResponse> discoverMovies(@Query("sort_by") String sortBy);

    @GET("movie/{id}/videos")
    Observable<VideoResponse> getMovieVideosbyId(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Observable<ReviewsResponse> getMovieReviewsById(@Path("id") int movieId);

    @GET("movie/{id}")
    Observable<MovieDetailsResponse> getMovieDetails(@Path("id") int movieId);

}
