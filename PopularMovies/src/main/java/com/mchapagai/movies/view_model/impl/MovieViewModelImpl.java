package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.api.MovieAPI;
import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.movies.view_model.MovieViewModel;

import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MovieViewModelImpl implements MovieViewModel {
    private final Provider<MovieAPI> movieAPI;

    public MovieViewModelImpl(Provider<MovieAPI> movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Flowable<MovieListResponse> discoverMovies(int page, String sortBy) {
        return movieAPI.get().discoverMovies(page, sortBy);
    }

    @Override
    public Observable<MovieDetailsResponse> fetchMovieDetailsByMovieId(int movieId) {
        return movieAPI.get().fetchMovieDetailsByMovieId(movieId);
    }

    @Override
    public Observable<MovieCreditResponse> getMovieCreditDetailsByCreditId(int movieId) {
        return movieAPI.get().getMovieCreditDetailsByCreditId(movieId);
    }

    @Override
    public Observable<VideoListResponse> getMovieVideosById(int movieId) {
        return movieAPI.get().getMovieVideosById(movieId);
    }

    @Override
    public Observable<ReviewListResponse> getMovieReviewsById(int movieId) {
        return movieAPI.get().getMovieReviewsById(movieId);
    }
}
