package com.example.mchapagai.view_model;

import com.example.mchapagai.model.binding.MovieResponse;

import io.reactivex.Observable;

public interface MovieViewModel {

    Observable<MovieResponse> discoverMovies(String sortBy);
}
