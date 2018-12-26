package com.mchapagai.library.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.mchapagai.library.R;
import com.mchapagai.library.common.LibraryConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

/**
 * @author Manorath Chapagai
 * <p>
 * Utility class to extract color gradient from the ImageView
 */

public class PaletteColorUtils {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LibraryConstants.LIGHT_COLOR, LibraryConstants.DARK_COLOR, LibraryConstants.UNKNOWN})
    public @interface ColorUtils {
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color, @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }

    public static @Nullable
    Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }

    /**
     * Determines if a given bitmap is dark. This extracts a palette inline so should not be called
     * with a large image!! If palette fails then check the color of the specified pixel
     */
    public static boolean isDark(@NonNull Bitmap bitmap, int backupPixelX, int backupPixelY) {
        // first try palette with a small color quant size
        Palette palette = Palette.from(bitmap).maximumColorCount(3).generate();
        if (palette != null && palette.getSwatches().size() > 0) {
            return isDark(palette) == LibraryConstants.DARK_COLOR;
        } else {
            // if palette failed, then check the color of the specified pixel
            return isDark(bitmap.getPixel(backupPixelX, backupPixelY));
        }
    }

    /**
     * Checks if the most populous color in the given palette is dark
     * <p/>
     * Annoyingly we have to return this Lightness 'enum' rather than a boolean as palette isn't
     * guaranteed to find the most populous color.
     */
    public static @ColorUtils
    int isDark(Palette palette) {
        Palette.Swatch mostPopulous = getMostPopulousSwatch(palette);
        if (mostPopulous == null) return LibraryConstants.UNKNOWN;
        return isDark(mostPopulous.getHsl()) ? LibraryConstants.DARK_COLOR : LibraryConstants.LIGHT_COLOR;
    }

    /**
     * Convert to HSL & check that the lightness value
     */
    public static boolean isDark(@ColorInt int color) {
        float[] hsl = new float[3];
        androidx.core.graphics.ColorUtils.colorToHSL(color, hsl);
        return isDark(hsl);
    }

    /**
     * Check that the lightness value (0–1)
     */
    public static boolean isDark(float[] hsl) { // @Size(3)
        return hsl[2] < 0.5f;
    }

    /**
     * Calculate a variant of the color to make it more suitable for overlaying information. Light
     * colors will be lightened and dark colors will be darkened
     *
     * @param color               the color to adjust
     * @param isDark              whether {@code color} is light or dark
     * @param lightnessMultiplier the amount to modify the color e.g. 0.1f will alter it by 10%
     * @return the adjusted color
     */
    public static @ColorInt
    int scrimify(@ColorInt int color,
                 boolean isDark,
                 @FloatRange(from = 0f, to = 1f) float lightnessMultiplier) {
        float[] hsl = new float[3];
        androidx.core.graphics.ColorUtils.colorToHSL(color, hsl);

        if (!isDark) {
            lightnessMultiplier += 1f;
        } else {
            lightnessMultiplier = 1f - lightnessMultiplier;
        }

        hsl[2] = LibraryUtils.constrain(0f, 1f, hsl[2] * lightnessMultiplier);
        return androidx.core.graphics.ColorUtils.HSLToColor(hsl);
    }

    /**
     * Create background gradient drawable from Image used using the Palette Library
     *
     * @param topColor    top gradient color (normal color)
     * @param centerColor center gradient color (light color)
     * @param bottomColor bottom gradient color (dark color)
     * @return gradientDrawable
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

    public static void setupTextColors(final TextView textView, Palette palette) {
        Palette.Swatch swatch = getMostPopulousSwatch(palette);
        if (swatch != null) {
            int startColor = ContextCompat.getColor(textView.getContext(), R.color.darkThemeSecondaryText);
            int endColor = swatch.getBodyTextColor();

            AnimationUtils.animateTextColorChange(textView, startColor, endColor);
        }
    }

}
