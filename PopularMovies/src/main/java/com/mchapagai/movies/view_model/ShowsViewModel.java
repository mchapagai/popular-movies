package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;

import io.reactivex.Observable;

public interface ShowsViewModel {

    Observable<OnTheAirResponse> discoverOnTheAirShows();

    Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId);

    Observable<ReviewListResponse> getReviewsById(int movieId);

}
