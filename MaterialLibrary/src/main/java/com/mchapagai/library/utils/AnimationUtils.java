package com.mchapagai.library.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.mchapagai.library.R;
import com.mchapagai.library.common.LibraryConstants;

public class AnimationUtils {

    private static Interpolator fastOutSlowIn;

    public static void setLightStatusBar(@NonNull View view) {
        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
    }

    public static Interpolator getFastOutSlowInInterpolator(Context context) {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = android.view.animation.AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in);
        }
        return fastOutSlowIn;
    }

    public static void animateTextColorChange(final TextView tv, int startColor, int endColor) {
        ValueAnimator textColorAnimation = ValueAnimator.ofArgb(startColor, endColor);
        textColorAnimation.addUpdateListener(animation -> tv.setTextColor((int) animation.getAnimatedValue()));
        textColorAnimation.setDuration(LibraryConstants.DURATION);
        textColorAnimation.setInterpolator(getFastOutSlowInInterpolator(tv.getContext()));
        textColorAnimation.start();
    }

    public static void resetTextColor(final TextView textView) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.darkThemeSecondaryText));
    }

    public static void animateBackgroundColorChange(final View view, int startColor, int endColor) {
        ValueAnimator infoBackgroundColorAnim = ValueAnimator.ofArgb(startColor, endColor);
        infoBackgroundColorAnim.addUpdateListener(animation -> view.setBackgroundColor((int) animation.getAnimatedValue()));
        infoBackgroundColorAnim.setDuration(LibraryConstants.DURATION);
        infoBackgroundColorAnim.setInterpolator(getFastOutSlowInInterpolator(view.getContext()));
        infoBackgroundColorAnim.start();
    }
}
