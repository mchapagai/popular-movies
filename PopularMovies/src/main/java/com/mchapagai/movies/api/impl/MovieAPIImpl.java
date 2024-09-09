package com.mchapagai.movies.api.impl;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.binding.PersonResponse;
import com.mchapagai.movies.service.MovieService;

import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MovieAPIImpl implements MovieAPI {

    private final Provider<MovieService> movieService;

    public MovieAPIImpl(Provider<MovieService> service) {
        this.movieService = service;
    }

    @Override
    public Flowable<MovieListResponse> discoverMovies(int page, String sortBy) {
        return movieService.get().discoverMovies(page, sortBy);
    }

    @Override
    public Observable<VideoListResponse> getMovieVideosbyId(int movieId) {
        return movieService.get().getMovieVideosById(movieId);
    }

    @Override
    public Observable<ReviewListResponse> getMovieReviewsById(int movieId) {
        return movieService.get().getMovieReviewsById(movieId);
    }

    @Override
    public Observable<MovieDetailsResponse> getMovieDetails(int movieId) {
        return movieService.get().getMovieDetails(movieId);
    }

    @Override
    public Observable<MovieCreditResponse> getMovieCreditDetails(int movieId) {
        return movieService.get().getMovieCreditDetailsByCreditId(movieId);
    }

    @Override
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return movieService.get().getPersonDetailsById(personId);
    }

    @Override
    public Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId) {
        return movieService.get().getPersonCombinedDetailsById(personId);
    }

    @Override
    public Observable<MovieListResponse> searchMovies(String query) {
        return movieService.get().searchMovies(query);
    }
}
