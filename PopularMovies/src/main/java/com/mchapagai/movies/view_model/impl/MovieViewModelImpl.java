package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;
import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.mchapagai.core.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MovieViewModelImpl implements MovieViewModel {

    private MovieAPI movieAPI;

    @Inject
    public MovieViewModelImpl(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Flowable<MovieListResponse> discoverMovies(int page, String sortBy) {
        return movieAPI.discoverMovies(page, sortBy).compose(RxUtils.INSTANCE.applyFlowableSchedulers());
    }

    @Override
    public Observable<VideoListResponse> getMovieVideosbyId(int movieId) {
        return movieAPI.getMovieVideosbyId(movieId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<ReviewListResponse> getMovieReviewsById(int movieId) {
        return movieAPI.getMovieReviewsById(movieId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<MovieDetailsResponse> getMovieDetails(int movieId) {
        return movieAPI.getMovieDetails(movieId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<MovieCreditResponse> getMovieCreditDetails(int movieId) {
        return movieAPI.getMovieCreditDetails(movieId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return movieAPI.getPersonDetailsById(personId).compose(RxUtils.INSTANCE.applySingleSchedulers());
    }

    @Override
    public Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId) {
        return movieAPI.getPersonCombinedDetailsById(personId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<MovieListResponse> searchMovies(String query) {
        return movieAPI.searchMovies(query).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }
}
