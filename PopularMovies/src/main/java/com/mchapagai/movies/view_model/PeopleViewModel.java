package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PeopleViewModel {
    Single<PersonResponse> getPersonDetailsById(int personId);
    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId);
}