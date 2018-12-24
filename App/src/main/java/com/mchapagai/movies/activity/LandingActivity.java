package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mchapagai.library.dialog.MaterialDialogFragment;
import com.mchapagai.library.dialog.MovieDialogBuilder;
import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.utils.PreferencesHelper;

import java.io.Serializable;

public class LandingActivity extends AppCompatActivity {

    private MaterialImageView launchPopularMovies;
    private MaterialImageView launchInfoScreen;
    private MaterialCircleImageView launchProfileScreen;
    private MaterialImageView launchSettingsScreen;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity_layout_container);

        preferencesHelper = new PreferencesHelper(this);

        launchPopularMovies = findViewById(R.id.landing_popular_movies_point);
        launchInfoScreen = findViewById(R.id.landing_about_page);
        launchSettingsScreen = findViewById(R.id.landing_settings);
        launchProfileScreen = findViewById(R.id.landing_user_profile);

        launchPopularMovies.setOnClickListener(view -> startActivity(new Intent(view.getContext(), DiscoverMoviesActivity.class)));
        launchInfoScreen.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AboutActivity.class)));
        launchSettingsScreen.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsActivity.class)));
        launchProfileScreen.setOnClickListener(view -> {
            // Check to see if user is logged in, if not show a dialog to prompt user to login
            if (!preferencesHelper.isSignedIn()) {
                MovieDialogBuilder builder = new MovieDialogBuilder()
                        .setMessage(getResources().getString(R.string.prompt_to_login_message))
                        .setTitle(getResources().getString(R.string.prompt_to_login_title))
                        .setLayoutResId(R.layout.confirmation_dialog)
                        .setPositiveButtonText(getResources().getString(R.string.material_dialog_ok))
                        .setNegativeButtonText(getResources().getString(R.string.material_dialog_cancel))
                        .setCustomButton(true);

                MaterialDialogFragment materialDialogFragment = MaterialDialogFragment.showDialog(builder, this);
                materialDialogFragment.setOnDialogClickListener(new MaterialDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onPositiveButtonClicked(Serializable data, String tag) {
                        Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onNegativeButtonClicked(String tag) {
                    }
                });
            } else if (preferencesHelper.isSignedIn()) {
                Intent intent = new Intent(view.getContext(), DiscoverMoviesActivity.class);
                startActivity(intent);
            }
        });
    }

}
