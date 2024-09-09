package com.mchapagai.movies.api.impl;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.movies.api.ShowsAPI;
import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import com.mchapagai.movies.service.ShowsService;

import javax.inject.Provider;

import io.reactivex.Observable;

public class ShowsAPIImpl implements ShowsAPI {

    private final Provider<ShowsService> showsService;

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
    public Observable<ReviewListResponse> getReviewsById(final int movieId) {
        return showsService.get().getReviewsById(movieId);
    }
}
