package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface MovieViewModel {
    Flowable<MovieListResponse> discoverMovies(int page, String sortBy);

    Observable<MovieDetailsResponse> fetchMovieDetailsByMovieId(int movieId);

    Observable<MovieCreditResponse> getMovieCreditDetailsByCreditId(int movieId);

    Observable<VideoListResponse> getMovieVideosById(int movieId);

    Observable<ReviewListResponse> getMovieReviewsById(int movieId);
}