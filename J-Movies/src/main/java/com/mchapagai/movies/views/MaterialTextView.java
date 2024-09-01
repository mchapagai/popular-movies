package com.mchapagai.movies.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.mchapagai.movies.R;

public class MaterialTextView extends AppCompatTextView {

    private static final int AUTO_LINK_NONE = 0;
    private String font;

    public MaterialTextView(Context context) {
        super(context);
        applyCustomFonts(context, null);
    }

    public MaterialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFonts(context, attrs);
    }

    public MaterialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFonts(context, attrs);
    }

    private void applyCustomFonts(Context context, AttributeSet attrs) {
        applyTheme(context, attrs);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat);
        setTypeface(typeface);
    }

    private void applyTheme(Context context, AttributeSet attrs) {
        int autoLinkMask = getAutoLinkMask();
        if (autoLinkMask == AUTO_LINK_NONE) return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialTextView);
        setLinkTextColor(a.getColor(
                R.styleable.MaterialTextView_linkColor,
                ContextCompat.getColor(context, R.color.linkColor)));
        a.recycle();
    }
}
