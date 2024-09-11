package com.mchapagai.movies.service;

import com.mchapagai.core.response.movies.MovieListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchService {

    @GET("search/movie")
    Observable<MovieListResponse> searchMovies(@Query("query") String q);

}
