package com.mchapagai.klutter.view_model.impl;

import com.mchapagai.klutter.api.ShowsAPI;
import com.mchapagai.klutter.model.binding.OnTheAirResponse;
import com.mchapagai.klutter.model.binding.ReviewsResponse;
import com.mchapagai.klutter.model.binding.ShowsDetailsResponse;
import com.mchapagai.klutter.utils.RxUtils;
import com.mchapagai.klutter.view_model.ShowsViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ShowsViewModelImpl implements ShowsViewModel {

    private ShowsAPI showsAPI;

    @Inject
    public ShowsViewModelImpl(ShowsAPI showsAPI) {
        this.showsAPI = showsAPI;
    }

    @Override
    public Observable<OnTheAirResponse> discoverOnTheAirShows() {
        return showsAPI.discoverOnTheAirShows().compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId) {
        return showsAPI.discoverShowsDetailsAppendVideos(tvId).compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<ReviewsResponse> getReviewsById(final int movieId) {
        return showsAPI.getReviewsById(movieId).compose(RxUtils.applySchedulers());
    }

}
