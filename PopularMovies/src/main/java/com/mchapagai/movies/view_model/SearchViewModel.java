package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.movies.MovieListResponse;

import io.reactivex.Observable;

public interface SearchViewModel {

    Observable<MovieListResponse> searchMovies(String query);

}
