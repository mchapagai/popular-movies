package com.mchapagai.movies.view_model.impl;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.movies.api.ShowsAPI;
import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import com.mchapagai.core.utils.RxUtils;
import com.mchapagai.movies.view_model.ShowsViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ShowsViewModelImpl implements ShowsViewModel {

    private final ShowsAPI showsAPI;

    @Inject
    public ShowsViewModelImpl(ShowsAPI showsAPI) {
        this.showsAPI = showsAPI;
    }

    @Override
    public Observable<OnTheAirResponse> discoverOnTheAirShows() {
        return showsAPI.discoverOnTheAirShows().compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId) {
        return showsAPI.discoverShowsDetailsAppendVideos(tvId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

    @Override
    public Observable<ReviewListResponse> getReviewsById(final int movieId) {
        return showsAPI.getReviewsById(movieId).compose(RxUtils.INSTANCE.applyObservableSchedulers());
    }

}
