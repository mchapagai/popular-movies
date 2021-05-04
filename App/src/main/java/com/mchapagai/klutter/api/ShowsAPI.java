package com.mchapagai.klutter.api;

import com.mchapagai.klutter.model.binding.OnTheAirResponse;
import com.mchapagai.klutter.model.binding.ReviewsResponse;
import com.mchapagai.klutter.model.binding.ShowsDetailsResponse;

import io.reactivex.Observable;

public interface ShowsAPI {

    Observable<OnTheAirResponse> discoverOnTheAirShows();

    Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId);

    Observable<ReviewsResponse> getReviewsById(int movieId);
}
