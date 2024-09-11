package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.view_model.SearchViewModel;
import com.mchapagai.core.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SearchViewModelImpl implements SearchViewModel {

    private final SearchAPI movieAPI;

    @Inject
    public SearchViewModelImpl(SearchAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    @Override
    public Observable<MovieListResponse> searchMovies(String query) {
        return movieAPI.searchMovies(query).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }
}
