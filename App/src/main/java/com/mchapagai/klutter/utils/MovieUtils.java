package com.mchapagai.klutter.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.mchapagai.klutter.common.Constants;
import com.mchapagai.klutter.model.Movies;

import androidx.appcompat.app.AppCompatActivity;

public class MovieUtils {

    /**
     * Helper methods to build Movie poster path using Poster Endpoint
     */

    private static final Uri MOVIE_POSTER_URI = Uri.parse(Constants.SECURE_IMAGE_ENDPOINT)
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

    /**
     * Method to calculate the width of the given image view
     *
     * @param context a parameter to access the application context
     * @return x-axis which is the thumbnail width
     */
    public static int calculateScreenWidth(Context context) {
        Point size = new Point();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int convertDpToPixel(Context context, int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (dp * displaymetrics.density + 0.5f);
    }
}
