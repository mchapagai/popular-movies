package com.mchapagai.movies.view_model;

import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import io.reactivex.Observable;

public interface ShowsViewModel {

    Observable<OnTheAirResponse> discoverOnTheAirShows();
    Observable<ShowsDetailsResponse> discoverShowsDetailsAppendVideos(int tvId);
    Observable<ReviewsResponse> getReviewsById(int movieId);

}
