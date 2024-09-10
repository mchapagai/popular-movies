package com.mchapagai.movies.view_model;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.PersonResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieViewModel {

    Flowable<MovieListResponse> discoverMovies(int page, String sortBy);

    Observable<VideoListResponse> getMovieVideosbyId(int movieId);

    Observable<ReviewListResponse> getMovieReviewsById(int movieId);

    Observable<MovieDetailsResponse> getMovieDetails(int movieId);

    Observable<MovieCreditResponse> getMovieCreditDetails(int movieId);

    Single<PersonResponse> getPersonDetailsById(int personId);

    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId);

    Observable<MovieListResponse> searchMovies(String query);

}
