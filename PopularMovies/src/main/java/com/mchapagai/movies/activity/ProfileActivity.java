package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.view_model.LoginViewModel;
import com.mchapagai.movies.views.MaterialCircleImageView;
import com.mchapagai.movies.views.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ProfileActivity extends BaseActivity {

//    @BindView(R.id.circle_image_view)
//    MaterialCircleImageView circleImageView;
//    @BindView(R.id.collapsing_toolbar_layout)
//    CollapsingToolbarLayout collapsingToolbar;
//    @BindView(R.id.profile_name)
//    MaterialTextView profileName;
//    @BindView(R.id.profile_movie_nav)
//    LinearLayout profileMovieLaunch;
//    @BindView(R.id.profile_show_nav)
//    LinearLayout profileShowLaunch;
//    @BindView(R.id.button_signout)
//    MaterialButton signoutButton;

    @Inject
    LoginViewModel loginViewModel;

    private PreferencesHelper preferencesHelper;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_container);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        preferencesHelper = new PreferencesHelper(this);

        collapsingToolbar.setTitle(" ");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        initViews();
    }

    private void initViews() {
        LinearLayout profileMovieLaunch = findViewById(R.id.profile_movie_nav);
        LinearLayout profileShowLaunch = findViewById(R.id.profile_show_nav);
        MaterialButton signoutButton = findViewById(R.id.button_signout);

        getAccountSignInDetails();
        profileMovieLaunch.setOnClickListener(
                view -> startActivity(new Intent(this, DiscoverMoviesActivity.class)));
        profileShowLaunch.setOnClickListener(
                view -> startActivity(new Intent(this, DiscoverOnTheAirActivity.class)));
        signoutButton.setOnClickListener(view -> {
            preferencesHelper.setSignedOut();
            Intent i = new Intent(view.getContext(), LandingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            ActivityCompat.finishAffinity(ProfileActivity.this);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAccountSignInDetails() {
        MaterialTextView profileName = findViewById(R.id.profile_name);
        MaterialCircleImageView circleImageView = findViewById(R.id.circle_image_view);

        String sessionId = preferencesHelper.setSessionKey();
        compositeDisposable.add(loginViewModel.getAccountDetails(sessionId)
                .subscribe(accountDetails -> {
                            if (accountDetails.getName() != null) {
                                profileName.setText(accountDetails.getName());
                            } else {
                                profileName.setText(accountDetails.getUsername());
                            }

                            Picasso.get().load(accountDetails.getAvatar().getGravatar().getHash())
                                    .placeholder(R.drawable.icon_user)
                                    .into(circleImageView);
                        }

                ));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
