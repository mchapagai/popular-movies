package com.mchapagai.movies.utils;

import android.net.Uri;

import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.Movies;

public class MovieUtils {

    /**
     * Helper methods to build Movie poster path using Poster Endpoint
     */

    private static final Uri MOVIE_POSTER_URI = Uri.parse(Constants.MOVIE_POSTER_ENDPOINT)
            .buildUpon()
            .build();

    private static Uri getPosterUriFromPath(String path) {
        return MOVIE_POSTER_URI
                .buildUpon()
                .appendEncodedPath(path)
                .build();
    }

    public static Uri getMoviePosterPathUri(Movies movies) {
        return getPosterUriFromPath(movies.getPosterPath());
    }

    public static Uri getMovieBackdropPathUri(Movies movies) {
        return getPosterUriFromPath(movies.getBackdropPath());
    }

}
