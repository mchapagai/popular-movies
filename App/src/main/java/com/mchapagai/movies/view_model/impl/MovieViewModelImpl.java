package com.mchapagai.movies.view_model.impl;

import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.model.binding.CreditResponse;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.binding.MovieDetailsResponse;
import com.mchapagai.movies.model.binding.MovieResponse;
import com.mchapagai.movies.model.binding.PersonResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.VideoResponse;
import com.mchapagai.movies.utils.RxUtils;
import com.mchapagai.movies.view_model.MovieViewModel;

import io.reactivex.Flowable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class MovieViewModelImpl implements MovieViewModel {

    private MovieAPI movieAPI;

    @Inject
    public MovieViewModelImpl(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Flowable<MovieResponse> discoverMovies(int page, String sortBy) {
        return movieAPI.discoverMovies(page, sortBy).compose(RxUtils.applyFlowableSchedulers());
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
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return movieAPI.getPersonDetailsById(personId).compose(RxUtils.applySingleSchedulers());
    }

    @Override
    public Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId) {
        return movieAPI.getPersonCombinedDetailsById(personId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<MovieResponse> searchMovies(String query) {
        return movieAPI.searchMovies(query).compose(RxUtils.applySchedulers());
    }
}
