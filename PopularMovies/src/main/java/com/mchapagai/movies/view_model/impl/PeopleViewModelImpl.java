package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.api.MovieAPI;
import com.mchapagai.core.api.PeopleAPI;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;
import com.mchapagai.movies.view_model.PeopleViewModel;

import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class PeopleViewModelImpl implements PeopleViewModel {

    private final Provider<PeopleAPI> peopleAPI;

    public PeopleViewModelImpl(Provider<PeopleAPI> peopleAPI) {
        this.peopleAPI = peopleAPI;
    }


    @Override
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return peopleAPI.get().getPersonDetailsById(personId);
    }

    @Override
    public Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId) {
        return peopleAPI.get().getPersonCombinedDetailsById(personId);
    }
}
