package com.mchapagai.library.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.mchapagai.library.R;

public class MaterialButton extends AppCompatButton {
    public MaterialButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        setTypeface(font);
    }
}
