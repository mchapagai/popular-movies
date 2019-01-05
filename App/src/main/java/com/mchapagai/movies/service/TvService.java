package com.mchapagai.movies.service;

import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TvService {

    @GET("tv/on_the_air")
    Observable<OnTheAirResponse> discoverOnTheAirShows();

}
