package com.mchapagai.movies.utils;

import android.net.Uri;

import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.Movies;
import com.mchapagai.movies.model.VideoItems;

public class MovieUtils {

    private static final Uri VIDEO_THUMBNAIL_BASE_URI = Uri.parse(Constants.YOUTUBE_THUMBNAIL);

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

    public static Uri getThumbnailUriForVideo(VideoItems video) {
        return VIDEO_THUMBNAIL_BASE_URI.buildUpon()
                .appendEncodedPath(video.getKey())
                .build();
    }

    public static Uri getAppUriForYoutubeVideo(VideoItems video) {
        return Uri.parse(Constants.YOUTUBE_APP_BASE_URI + video.getKey());
    }

    public static Uri getWebUriForYoutubeVideo(VideoItems video) {
        return YOUTUBE_WEB_BASE_URI.buildUpon()
                .appendQueryParameter(Constants.YOUTUBE_WEB_QUERY_PARAM, video.getKey())
                .build();
    }

    private static final Uri YOUTUBE_WEB_BASE_URI = Uri.parse(Constants.YOUTUBE_WEB_BASE_URL)
            .buildUpon()
            .appendPath(Constants.YOUTUBE_WEB_VIDEO_PATH)
            .build();
}
