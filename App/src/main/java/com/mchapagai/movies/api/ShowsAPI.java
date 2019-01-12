package com.mchapagai.movies.api;

import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import io.reactivex.Observable;

public interface ShowsAPI {

    Observable<OnTheAirResponse> discoverOnTheAirShows();
    Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId);
    Observable<ReviewsResponse> getReviewsById(int movieId);
}
