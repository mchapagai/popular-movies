package com.mchapagai.movies.api;

import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import io.reactivex.Observable;

public interface TvAPI {

    Observable<OnTheAirResponse> discoverOnTheAirShows();

}
