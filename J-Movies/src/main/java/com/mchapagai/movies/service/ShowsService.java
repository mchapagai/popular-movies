package com.mchapagai.movies.service;

import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
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
    Observable<ReviewsResponse> getReviewsById(@Path("id") int movieId);
}
