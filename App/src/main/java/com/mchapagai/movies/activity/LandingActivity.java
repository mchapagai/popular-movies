package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mchapagai.movies.R;
import com.mchapagai.movies.dialog.MaterialDialogFragment;
import com.mchapagai.movies.dialog.MovieDialogBuilder;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.views.MaterialCircleImageView;
import com.mchapagai.movies.views.MaterialTextView;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.landing_popular_movies)
    MaterialTextView launchPopularMovies;
    @BindView(R.id.landing_popular_shows)
    MaterialTextView launchPopularShows;
    @BindView(R.id.landing_about_page_layout)
    ConstraintLayout launchInfoScreen;
    @BindView(R.id.landing_user_profile)
    MaterialCircleImageView launchProfileScreen;

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.landing_activity_layout_container);
        ButterKnife.bind(this);

        launchPopularMovies.setOnClickListener(
                view -> startActivity(new Intent(view.getContext(), DiscoverMoviesActivity.class)));
        animateCircle(launchPopularMovies);
        launchInfoScreen
                .setOnClickListener(
                        view -> startActivity(new Intent(view.getContext(), AboutActivity.class)));
        launchPopularShows.
                setOnClickListener(
                        view -> startActivity(
                                new Intent(view.getContext(), DiscoverOnTheAirActivity.class)));
        animateCircle(launchPopularShows);
        launchProfileScreen.setOnClickListener(view -> {
            // Check to see if user is logged in, if not show a dialog to prompt user to login
            if (!preferencesHelper.isSignedIn()) {
                MovieDialogBuilder builder = new MovieDialogBuilder()
                        .setMessage(getResources().getString(R.string.prompt_to_login_message))
                        .setTitle(getResources().getString(R.string.prompt_to_login_title))
                        .setLayoutResId(R.layout.confirmation_dialog)
                        .setPositiveButtonText(
                                getResources().getString(R.string.material_dialog_ok))
                        .setNegativeButtonText(
                                getResources().getString(R.string.material_dialog_cancel))
                        .setCustomButton(true);

                MaterialDialogFragment materialDialogFragment = MaterialDialogFragment.showDialog(
                        builder, this);
                materialDialogFragment.setOnDialogClickListener(
                        new MaterialDialogFragment.OnDialogClickListener() {
                            @Override
                            public void onPositiveButtonClicked(Serializable data, String tag) {
                                Intent intent = new Intent(LandingActivity.this,
                                        LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegativeButtonClicked(String tag) {
                            }
                        });
            } else if (preferencesHelper.isSignedIn()) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void animateCircle(TextView textView) {
        TranslateAnimation anim = new TranslateAnimation(-25, 25, -25, 25);
        anim.setDuration(600);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        anim.setRepeatMode(Animation.RELATIVE_TO_SELF);
        anim.setRepeatCount(1);
        textView.startAnimation(anim);
    }

}
