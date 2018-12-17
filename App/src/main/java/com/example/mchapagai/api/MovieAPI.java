package com.example.mchapagai.api;

import com.example.mchapagai.model.binding.*;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieAPI {

    Observable<MovieResponse> discoverMovies(String sortBy);
    Observable<VideoResponse> getMovieVideosbyId(int movieId);
    Observable<ReviewsResponse> getMovieReviewsById(int movieId);
    Observable<MovieDetailsResponse> getMovieDetails(int movieId);
    Observable<CreditResponse> getMovieCreditDetails(int movieId);
    Single<PersonResponse> getPersonDetailsById(String movieId);

}
