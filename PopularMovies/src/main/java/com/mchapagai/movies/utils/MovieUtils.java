package com.mchapagai.movies.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MovieUtils {
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
