package com.example.mchapagai.view_model;

import com.example.mchapagai.model.binding.MovieResponse;
import com.example.mchapagai.model.binding.ReviewsResponse;
import com.example.mchapagai.model.binding.VideoResponse;

import io.reactivex.Observable;

public interface MovieViewModel {

    Observable<MovieResponse> discoverMovies(String sortBy);
    Observable<VideoResponse> getMovieVideosbyId(int movieId);
    Observable<ReviewsResponse> getMovieReviewsById(int movieId);
}
