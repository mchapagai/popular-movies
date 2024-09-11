package com.mchapagai.movies.api.impl;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.service.MovieSearchService;

import javax.inject.Provider;

import io.reactivex.Observable;

public class SearchAPIImpl implements SearchAPI {

    private final Provider<MovieSearchService> movieService;

    public SearchAPIImpl(Provider<MovieSearchService> service) {
        this.movieService = service;
    }

    @Override
    public Observable<MovieListResponse> searchMovies(String query) {
        return movieService.get().searchMovies(query);
    }
}
