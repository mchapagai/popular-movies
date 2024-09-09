package com.mchapagai.movies.service;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShowsService {

    @GET("tv/on_the_air")
    Observable<OnTheAirResponse> discoverOnTheAirShows();

    @GET("tv/{tvId}?append_to_response=videos")
    Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(@Path("tvId") int tvId);

    @GET("movie/{id}/reviews")
    Observable<ReviewListResponse> getReviewsById(@Path("id") int movieId);
}
