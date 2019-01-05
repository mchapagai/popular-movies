package com.mchapagai.movies.view_model;

import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import io.reactivex.Observable;

public interface TvViewModel {

    Observable<OnTheAirResponse> discoverOnTheAirShows();

}
