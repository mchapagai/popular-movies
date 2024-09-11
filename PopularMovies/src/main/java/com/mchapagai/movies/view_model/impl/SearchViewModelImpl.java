package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.view_model.SearchViewModel;
import com.mchapagai.core.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class SearchViewModelImpl implements SearchViewModel {

    private final SearchAPI movieAPI;

    @Inject
    public SearchViewModelImpl(SearchAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Single<PersonResponse> getPersonDetailsById(int personId) {
        return movieAPI.getPersonDetailsById(personId).compose(RxUtils.INSTANCE.applySingleSchedulers());
    }

    @Override
    public Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId) {
        return movieAPI.getPersonCombinedDetailsById(personId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<MovieListResponse> searchMovies(String query) {
        return movieAPI.searchMovies(query).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }
}
