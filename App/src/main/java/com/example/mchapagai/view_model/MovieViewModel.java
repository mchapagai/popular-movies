package com.example.mchapagai.view_model;

import com.example.mchapagai.model.binding.*;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieViewModel {

    Observable<MovieResponse> discoverMovies(String sortBy);
    Observable<VideoResponse> getMovieVideosbyId(int movieId);
    Observable<ReviewsResponse> getMovieReviewsById(int movieId);
    Observable<MovieDetailsResponse> getMovieDetails(int movieId);
    Observable<CreditResponse> getMovieCreditDetails(int movieId);
    Single<PersonResponse> getPersonDetailsById(String movieId);

}
