package com.example.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.library.R;

import org.apache.commons.lang3.StringUtils;

public class PageLoader extends RelativeLayout {

    private RelativeLayout pageLayout;
    private TextView headerTextView;
    private TextView primaryTextView;
    private RelativeLayout animateView;

    public PageLoader(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(getContext(), R.layout.page_loader_container, this);
        pageLayout = findViewById(R.id.page_loader);
        headerTextView = findViewById(R.id.page_loader_header_text);
        primaryTextView = findViewById(R.id.page_loader_primary_text);
        animateView = findViewById(R.id.page_loader_layout_child);

        setupAttributes(attrs);
    }

    private void setupAttributes(AttributeSet attrs) {
        TypedArray attributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PageLoader, 0, 0);

        try {
            String headerText = attributes.getString(R.styleable.PageLoader_loaderHeaderText);
            String primaryText = attributes.getString(R.styleable.PageLoader_loaderPrimaryText);
            boolean shouldShowAnimation = attributes.getBoolean(R.styleable.PageLoader_showShowAnimationView, false);

            if (StringUtils.isNotEmpty(headerText)) {
                headerTextView.setText(headerText);
                headerTextView.setVisibility(VISIBLE);
            }

            if (StringUtils.isNotEmpty(primaryText)) {
                primaryTextView.setText(headerText);
                primaryTextView.setVisibility(VISIBLE);
            }

            if (shouldShowAnimation) {
                animateView.setVisibility(VISIBLE);
            }
        } finally {
            attributes.recycle();
        }
    }

}
