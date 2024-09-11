package com.mchapagai.movies.api.impl;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.service.MovieSearchService;

import javax.inject.Provider;

import io.reactivex.Observable;
import io.reactivex.Single;

public class SearchAPIImpl implements SearchAPI {

    private final Provider<MovieSearchService> movieService;

    public SearchAPIImpl(Provider<MovieSearchService> service) {
        this.movieService = service;
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
