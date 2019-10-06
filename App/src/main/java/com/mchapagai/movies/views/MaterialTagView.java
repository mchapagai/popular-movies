package com.mchapagai.movies.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.mchapagai.movies.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MaterialTagView extends RelativeLayout {

    private List<String> colorItems = Arrays.asList("#FF4500", "#003366", "#6AC68D", "#1CA8F4");
    private int defaultBackgroundColor = R.color.actionColor;
    private int defaultBackgroundCOlorPressed = R.color.darkThemePrimary;
    private boolean useRandomColor;
    private String tagText;
    private int backgroundColor;
    private int textColor;
    private TextView tagTextView;

    public MaterialTagView(Context context) {
        this(context, null, 0);
    }

    public MaterialTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        configureTagView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams thisParams = getLayoutParams();
        thisParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        thisParams.height = (int) getResources().getDimension(R.dimen.margin_32dp);
        this.setLayoutParams(thisParams);
    }

    private void configureTagView() {
        initBackgroundColor();
        initTextView();
    }


    @SuppressLint("ResourceType")
    private void initTextView() {
        if (!ViewCompat.isAttachedToWindow(this)) {
            return;
        }

        if (tagTextView == null) {
            tagTextView = new TextView(getContext());
        }

        LayoutParams chipTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        chipTextParams.addRule(CENTER_IN_PARENT);
        chipTextParams.setMargins(
                (int) getResources().getDimension(R.dimen.dimen_12dp),
                0,
                (int) getResources().getDimension(R.dimen.dimen_12dp),
                0
        );

        tagTextView.setLayoutParams(chipTextParams);
        tagTextView.setTextColor(textColor);
        tagTextView.setText(tagText);
        tagTextView.setId(0x00059118);

        this.removeView(tagTextView);
        this.addView(tagTextView);
    }

    private void initBackgroundColor() {
        GradientDrawable bgDrawable = new GradientDrawable();
        bgDrawable.setShape(GradientDrawable.RECTANGLE);
        bgDrawable.setColor(backgroundColor);
        setBackground(getSelector());
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.MaterialTagView, 0, 0);

        tagText = ta.getString(R.styleable.MaterialTagView_tagviewText);
        backgroundColor = ta.getColor(R.styleable.MaterialTagView_tagviewBackgroundColor,
                ContextCompat.getColor(getContext(), R.color.tagViewBackgroundColor));
        textColor = ta.getColor(R.styleable.MaterialTagView_tagviewTextColor,
                ContextCompat.getColor(getContext(), R.color.darkThemePrimaryText));

        ta.recycle();
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
        requestLayout();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        throw new RuntimeException("Use changeBackgroundColor instead of setBackgroundColor");
    }

    private StateListDrawable getSelector() {
        return getSelectorNormal();
    }

    public void setUseRandomColor(boolean useRandomColor) {
        this.useRandomColor = useRandomColor;
    }

    @SuppressLint("ResourceAsColor")
    private StateListDrawable getSelectorNormal() {
        StateListDrawable states = new StateListDrawable();

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(defaultBackgroundCOlorPressed);
        int radius = 8;
        gradientDrawable.setCornerRadius(radius);

        states.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);

        gradientDrawable = new GradientDrawable();
        int index = new Random().nextInt(colorItems.size());
        if (useRandomColor) defaultBackgroundColor = Color.parseColor(colorItems.get(index));
        gradientDrawable.setColor(defaultBackgroundColor);
        gradientDrawable.setCornerRadius(radius);

        states.addState(new int[]{}, gradientDrawable);

        return states;
    }
}