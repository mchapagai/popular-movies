package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface SearchViewModel {

    Single<PersonResponse> getPersonDetailsById(int personId);

    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId);

    Observable<MovieListResponse> searchMovies(String query);

}
