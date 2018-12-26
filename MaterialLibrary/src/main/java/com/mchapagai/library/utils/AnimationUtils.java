package com.mchapagai.library.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mchapagai.library.R;
import com.mchapagai.library.common.LibraryConstants;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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

    public static int getTargetHeight(View v){
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(widthSpec, heightSpec);

        return v.getMeasuredHeight();
    }

    public static Animation getExpandHeightAnimation(final View v, final int targetHeight){
        return new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
    }
}
