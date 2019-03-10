package com.mchapagai.movies.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mchapagai.library.views.MaterialButton;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    private int[] layouts;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.skip_button)
    MaterialButton skipButton;
    @BindView(R.id.next_button)
    MaterialButton nextButton;
    @BindView(R.id.layout_dots)
    LinearLayout dotsLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.welcome_activity_container);
        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.welcome_screen_movies,
                R.layout.welcome_screen_tv_shows,
                R.layout.welcome_screen_ticketing};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        skipButton.setOnClickListener(v -> launchHomeScreen());

        nextButton.setOnClickListener(v -> {
            // checking for last page
            // if last page home screen will be launched
            int current = getItem();
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        final TextView[] dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    private int getItem() {
        return viewPager.getCurrentItem() + 1;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(WelcomeActivity.this, LandingActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener =
            new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position);

                    // changing the next button text 'NEXT' / 'GOT IT'
                    if (position == layouts.length - 1) {
                        // last page. make button text to GOT IT
                        nextButton.setText(getString(R.string.button_got_it).toUpperCase());
                        skipButton.setVisibility(View.GONE);
                    } else {
                        // still pages are left
                        nextButton.setText(getString(R.string.button_next).toUpperCase());
                        skipButton.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,
                @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
