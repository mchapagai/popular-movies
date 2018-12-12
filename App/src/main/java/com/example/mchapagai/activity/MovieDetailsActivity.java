package com.example.mchapagai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.library.views.MaterialImageView;
import com.example.mchapagai.R;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.Movies;
import com.example.mchapagai.utils.RoundedTransformation;
import com.example.mchapagai.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Inject;

public class MovieDetailsActivity extends BaseActivity {

    private Movies movies;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private MaterialImageView posterBackdropImageView;
    private TextView originalTitle, releaseDate, ratings, overview;

    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity_container);

        movies = getIntent().getParcelableExtra(Constants.MOVIE_DETAILS);

        initViews();
    }

    private void initViews() {
        // Fullscreen - hide the status bar
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = findViewById(R.id.movie_details_toolbar);
        appBarLayout = findViewById(R.id.appbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        posterBackdropImageView = findViewById(R.id.details_backdrop);
        originalTitle = findViewById(R.id.original_title);
        releaseDate = findViewById(R.id.release_date);
        ratings = findViewById(R.id.ratings);
        overview = findViewById(R.id.details_over_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        // Implement addOnOffsetChangedListener to show CollapsingToolbarLayout Tile only when collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = true;

            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movies.getOriginalTitle());
                    isShown = true;
                } else if (isShown) {
                    // There should be a space between double quote otherwise it won't work
                    collapsingToolbar.setTitle(" ");
                    isShown = false;
                }
            }
        });

        originalTitle.setText(movies.getTitle());
        overview.setText(movies.getOverview());
        releaseDate.setText(movies.getReleaseDate());
        ratings.setText(String.format(Locale.US, "%.2f", movies.getVoteAverage()));
        Picasso.get().load(Constants.MOVIE_DETAILS_POSTER_URL + movies.getBackdropPath())
                .transform(new RoundedTransformation(20, 0))
                .into(posterBackdropImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
