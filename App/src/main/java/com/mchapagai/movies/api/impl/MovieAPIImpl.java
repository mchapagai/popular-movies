package com.mchapagai.movies.api.impl;

import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.model.binding.CreditResponse;
import com.mchapagai.movies.model.binding.CreditResponseCombined;
import com.mchapagai.movies.model.binding.MovieDetailsResponse;
import com.mchapagai.movies.model.binding.MovieResponse;
import com.mchapagai.movies.model.binding.PersonResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.VideoResponse;
import com.mchapagai.movies.service.MovieService;

import javax.inject.Provider;

import io.reactivex.Observable;
import io.reactivex.Single;

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

    @Override
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return movieService.get().getPersonDetailsById(personId);
    }

    @Override
    public Observable<CreditResponseCombined> getPersonCombinedDetailsById(int personId) {
        return movieService.get().getPersonCombinedDetailsById(personId);
    }
}
