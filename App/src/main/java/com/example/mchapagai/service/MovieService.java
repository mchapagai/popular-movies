package com.example.mchapagai.service;

import com.example.mchapagai.model.binding.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MovieService {

    @GET("discover/movie")
    Observable<MovieResponse> discoverMovies();

}
