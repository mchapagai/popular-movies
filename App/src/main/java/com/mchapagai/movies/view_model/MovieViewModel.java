package com.mchapagai.movies.view_model;

import com.mchapagai.movies.model.movies.binding.CreditResponse;
import com.mchapagai.movies.model.movies.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.movies.binding.MovieDetailsResponse;
import com.mchapagai.movies.model.movies.binding.MovieResponse;
import com.mchapagai.movies.model.movies.binding.PersonResponse;
import com.mchapagai.movies.model.movies.binding.ReviewsResponse;
import com.mchapagai.movies.model.movies.binding.VideoResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieViewModel {

    Observable<MovieResponse> discoverMovies(String sortBy);
    Observable<VideoResponse> getMovieVideosbyId(int movieId);
    Observable<ReviewsResponse> getMovieReviewsById(int movieId);
    Observable<MovieDetailsResponse> getMovieDetails(int movieId);
    Observable<CreditResponse> getMovieCreditDetails(int movieId);
    Single<PersonResponse> getPersonDetailsById(int personId);
    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(int personId);

}
