package com.mchapagai.movies.view_model.impl;

import com.mchapagai.movies.api.TvAPI;
import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import com.mchapagai.movies.utils.RxUtils;
import com.mchapagai.movies.view_model.TvViewModel;
import io.reactivex.Observable;
import javax.inject.Inject;

public class TvViewModelImpl implements TvViewModel {

    private TvAPI tvAPI;

    @Inject
    public TvViewModelImpl(TvAPI tvAPI) {
        this.tvAPI = tvAPI;
    }

    @Override
    public Observable<OnTheAirResponse> discoverOnTheAirShows() {
        return tvAPI.discoverOnTheAirShows().compose(RxUtils.applySchedulers());
    }
}
