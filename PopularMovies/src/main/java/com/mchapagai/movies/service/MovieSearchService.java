package com.mchapagai.movies.service;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSearchService {

    @GET("person/{personId}")
    Single<PersonResponse> getPersonDetailsById(@Path("personId") int id);

    @GET("person/{personId}/combined_credits")
    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(@Path("personId") int personId);

    @GET("search/movie")
    Observable<MovieListResponse> searchMovies(@Query("query") String q);

}
