package com.mchapagai.movies.api;

import com.mchapagai.core.response.movies.MovieListResponse;

import io.reactivex.Observable;

public interface SearchAPI {

    Observable<MovieListResponse> searchMovies(String query);
}
