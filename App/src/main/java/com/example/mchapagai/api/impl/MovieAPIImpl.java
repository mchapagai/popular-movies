package com.example.mchapagai.api.impl;

import com.example.mchapagai.api.MovieAPI;
import com.example.mchapagai.model.binding.*;
import com.example.mchapagai.service.MovieService;

import javax.inject.Provider;

import io.reactivex.Observable;

public class MovieAPIImpl implements MovieAPI {

    private Provider<MovieService> movieService;

    public MovieAPIImpl(Provider<MovieService> service) {
        this.movieService = service;
    }

    @Override
    public Observable<MovieResponse> discoverMovies(String sortBy) {
        return movieService.get().discoverMovies(sortBy);
    }

    @Override
    public Observable<VideoResponse> getMovieVideosbyId(int movieId) {
        return movieService.get().getMovieVideosbyId(movieId);
    }

    @Override
    public Observable<ReviewsResponse> getMovieReviewsById(int movieId) {
        return movieService.get().getMovieReviewsById(movieId);
    }

    @Override
    public Observable<MovieDetailsResponse> getMovieDetails(int movieId) {
        return movieService.get().getMovieDetails(movieId);
    }

    @Override
    public Observable<CreditResponse> getMovieCreditDetails(int movieId) {
        return movieService.get().getMovieCreditDetails(movieId);
    }
}
