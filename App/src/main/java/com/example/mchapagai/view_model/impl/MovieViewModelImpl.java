package com.example.mchapagai.view_model.impl;

import com.example.mchapagai.model.binding.MovieResponse;
import com.example.mchapagai.model.binding.ReviewsResponse;
import com.example.mchapagai.model.binding.VideoResponse;
import com.example.mchapagai.service.MovieService;
import com.example.mchapagai.utils.RxUtils;
import com.example.mchapagai.view_model.MovieViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Observable;

public class MovieViewModelImpl implements MovieViewModel {

    private Provider<MovieService> movieService;

    @Inject
    public MovieViewModelImpl(Provider<MovieService> movieService) {
        this.movieService = movieService;
    }

    @Override
    public Observable<MovieResponse> discoverMovies(String sortBy) {
        return movieService.get().discoverMovies(sortBy).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<VideoResponse> getMovieVideosbyId(int movieId) {
        return movieService.get().getMovieVideosbyId(movieId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<ReviewsResponse> getMovieReviewsById(int movieId) {
        return movieService.get().getMovieReviewsById(movieId).compose(RxUtils.applySchedulers());
    }
}
