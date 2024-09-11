package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.shows.ShowListResponse;
import com.mchapagai.core.response.shows.ShowsDetailsResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface ShowsViewModel {

    Flowable<ShowListResponse> discoverShows(int page, String sortBy);

    Observable<ShowsDetailsResponse> fetchShowDetailsById(int showId);

    Observable<ReviewListResponse> fetchShowReviewsById(int showId);

}