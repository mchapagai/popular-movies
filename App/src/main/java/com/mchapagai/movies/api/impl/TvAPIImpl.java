package com.mchapagai.movies.api.impl;

import com.mchapagai.movies.api.TvAPI;
import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import com.mchapagai.movies.service.TvService;
import io.reactivex.Observable;
import javax.inject.Provider;

public class TvAPIImpl implements TvAPI {

    private Provider<TvService> tvService;

    public TvAPIImpl(Provider<TvService> service) {
        this.tvService = service;
    }

    @Override
    public Observable<OnTheAirResponse> discoverOnTheAirShows() {
        return tvService.get().discoverOnTheAirShows();
    }
}
