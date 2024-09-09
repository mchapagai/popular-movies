package com.mchapagai.movies.service;

import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.binding.PersonResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie")
    Flowable<MovieListResponse> discoverMovies(@Query("page") int page,
                                               @Query("sort_by") String sortBy);

    @GET("movie/{id}/videos")
    Observable<VideoListResponse> getMovieVideosById(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Observable<ReviewListResponse> getMovieReviewsById(@Path("id") int movieId);

    @GET("movie/{id}")
    Observable<MovieDetailsResponse> getMovieDetails(@Path("id") int movieId);

    @GET("movie/{id}/credits")
    Observable<MovieCreditResponse> getMovieCreditDetailsByCreditId(@Path("id") int movieId);

    @GET("person/{personId}")
    Single<PersonResponse> getPersonDetailsById(@Path("personId") int id);

    @GET("person/{personId}/combined_credits")
    Observable<CombinedPersonResponse> getPersonCombinedDetailsById(@Path("personId") int personId);

    @GET("search/movie")
    Observable<MovieListResponse> searchMovies(@Query("query") String q);

}
