package com.mchapagai.klutter.view_model;

import com.mchapagai.klutter.model.binding.CreditResponse;
import com.mchapagai.klutter.model.binding.CombinedPersonResponse;
import com.mchapagai.klutter.model.binding.MovieDetailsResponse;
import com.mchapagai.klutter.model.binding.MovieResponse;
import com.mchapagai.klutter.model.binding.PersonResponse;
import com.mchapagai.klutter.model.binding.ReviewsResponse;
import com.mchapagai.klutter.model.binding.VideoResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieViewModel {

    Flowable<MovieResponse> discoverMovies(int page, String sortBy);

    Observable<VideoResponse> getMovieVideosbyId(int movieId);

    Observable<ReviewsResponse> getMovieReviewsById(int movieId);

    Observable<MovieDetailsResponse> getMovieDetails(int movieId);

    Observable<CreditResponse> getMovieCreditDetails(int movieId);

    Single<PersonResponse> getPersonDetailsById(int personId);

    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId);

    Observable<MovieResponse> searchMovies(String query);

}
