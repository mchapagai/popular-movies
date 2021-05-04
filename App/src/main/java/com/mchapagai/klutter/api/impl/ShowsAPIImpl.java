package com.mchapagai.klutter.api.impl;

import com.mchapagai.klutter.api.ShowsAPI;
import com.mchapagai.klutter.model.binding.OnTheAirResponse;
import com.mchapagai.klutter.model.binding.ReviewsResponse;
import com.mchapagai.klutter.model.binding.ShowsDetailsResponse;
import com.mchapagai.klutter.service.ShowsService;

import javax.inject.Provider;

import io.reactivex.Observable;

public class ShowsAPIImpl implements ShowsAPI {

    private Provider<ShowsService> showsService;

    public ShowsAPIImpl(Provider<ShowsService> service) {
        this.showsService = service;
    }

    @Override
    public Observable<OnTheAirResponse> discoverOnTheAirShows() {
        return showsService.get().discoverOnTheAirShows();
    }

    @Override
    public Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId) {
        return showsService.get().discoverShowsDetailsAppendVideos(tvId);
    }

    @Override
    public Observable<ReviewsResponse> getReviewsById(final int movieId) {
        return showsService.get().getReviewsById(movieId);
    }
}
