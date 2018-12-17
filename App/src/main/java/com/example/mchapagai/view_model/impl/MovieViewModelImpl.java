package com.example.mchapagai.view_model.impl;

import com.example.mchapagai.api.MovieAPI;
import com.example.mchapagai.model.binding.*;
import com.example.mchapagai.utils.RxUtils;
import com.example.mchapagai.view_model.MovieViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;

public class MovieViewModelImpl implements MovieViewModel {

    private MovieAPI movieAPI;

    @Inject
    public MovieViewModelImpl(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Observable<MovieResponse> discoverMovies(String sortBy) {
        return movieAPI.discoverMovies(sortBy).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<VideoResponse> getMovieVideosbyId(int movieId) {
        return movieAPI.getMovieVideosbyId(movieId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<ReviewsResponse> getMovieReviewsById(int movieId) {
        return movieAPI.getMovieReviewsById(movieId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<MovieDetailsResponse> getMovieDetails(int movieId) {
        return movieAPI.getMovieDetails(movieId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<CreditResponse> getMovieCreditDetails(int movieId) {
        return movieAPI.getMovieCreditDetails(movieId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Single<PersonResponse> getPersonDetailsById(String movieId) {
        return movieAPI.getPersonDetailsById(movieId).compose(RxUtils.applySingleSchedulers());
    }
}
