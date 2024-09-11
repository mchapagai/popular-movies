package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.api.ShowAPI;
import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.shows.ShowListResponse;
import com.mchapagai.core.response.shows.ShowsDetailsResponse;
import com.mchapagai.movies.view_model.ShowsViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ShowsViewModelImpl implements ShowsViewModel {

    private final Provider<ShowAPI> showsAPI;

    @Inject
    public ShowsViewModelImpl(Provider<ShowAPI> showsAPI) {
        this.showsAPI = showsAPI;
    }

    @Override
    public Flowable<ShowListResponse> discoverShows(int page, String sortBy) {
        return showsAPI.get().discoverShows(page, sortBy);
    }

    @Override
    public Observable<ShowsDetailsResponse> fetchShowDetailsById(int showId) {
        return showsAPI.get().fetchShowDetailsById(showId);
    }

    @Override
    public Observable<ReviewListResponse> fetchShowReviewsById(final int showId) {
        return showsAPI.get().fetchShowReviewsById(showId);
    }

}
