package com.mchapagai.library.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;

public class PaletteColorUtils {

    /**
     * Create background gradient drawable from Image used using the Palette Library
     *
     * @param topColor    top gradient color (normal color)
     * @param centerColor center gradient color (light color)
     * @param bottomColor bottom gradient color (dark color)
     * @return
     */
    public static GradientDrawable getGradientDrawable(int topColor, int centerColor, int bottomColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColors(new int[]{
                topColor,
                centerColor,
                bottomColor
        });
        return gradientDrawable;
    }

    /**
     * @param palette generated palette from image
     * @return return top color for gradient either muted or vibrant
     */
    public static int topColor(Palette palette) {
        if (palette.getMutedSwatch() != null || palette.getVibrantSwatch() != null)
            return palette.getMutedSwatch() != null ? palette.getMutedSwatch().getRgb() : palette.getVibrantSwatch().getRgb();
        else return Color.RED;
    }

    /**
     * @param palette generated palette from image
     * @return return center light color for gradient either muted or vibrant
     */
    public static int centerLightColor(Palette palette) {
        if (palette.getLightMutedSwatch() != null || palette.getLightVibrantSwatch() != null)
            return palette.getLightMutedSwatch() != null ? palette.getLightMutedSwatch().getRgb() : palette.getLightVibrantSwatch().getRgb();
        else return Color.GREEN;
    }

    /**
     * @param palette generated palette from image
     * @return return bottom dark color for gradient either muted or vibrant
     */
    public static int bottomDarkColor(Palette palette) {
        if (palette.getDarkMutedSwatch() != null || palette.getDarkVibrantSwatch() != null)
            return palette.getDarkMutedSwatch() != null ? palette.getDarkMutedSwatch().getRgb() : palette.getDarkVibrantSwatch().getRgb();
        else return Color.BLUE;
    }
}
