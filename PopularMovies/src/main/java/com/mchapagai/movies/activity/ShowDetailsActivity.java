package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.ShowDetailsAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.utils.DateTimeUtils;
import com.mchapagai.movies.utils.LibraryUtils;
import com.mchapagai.movies.utils.MaterialDialogUtils;
import com.mchapagai.movies.view_model.ShowsViewModel;
import com.mchapagai.movies.views.MaterialTextView;
import com.mchapagai.movies.widget.AppBarStateChangeListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ShowDetailsActivity extends BaseActivity {

    ImageView showDetailsBackdrop;
    FloatingActionButton showsFavorite;
    Toolbar showsToolbar;
    private OnTheAir onTheAir;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ShowsViewModel showsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shows_details_activity_container);

        showsToolbar = findViewById(R.id.shows_toolbar);
        showsFavorite = findViewById(R.id.shows_favorite);
        showDetailsBackdrop = findViewById(R.id.show_details_backdrop);
        onTheAir = getIntent().getParcelableExtra(Constants.SHOWS_DETAILS);

        // Bottom slide animation
        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        postponeEnterTransition();

        initViews();
        setupTablayout();
    }

    private void initViews() {
        MaterialTextView showsTitle = findViewById(R.id.shows_title);
        // Fullscreen - hide the status bar
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(showsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("");

        showsTitle.setText(onTheAir.getName());
        Picasso.get().load(Constants.SECURE_BASE_URL + onTheAir.getBackdropPath()).into(
                showDetailsBackdrop);

        showsFavorite.setOnClickListener(
                v -> LibraryUtils
                        .showSnackBar(this, v, getString(R.string.prompt_to_login_message)));
    }

    private void setupTablayout() {
        AppBarLayout showsAppbar = findViewById(R.id.shows_appbar);
        TabLayout showsTabLayout = findViewById(R.id.shows_tab_layout);
        ViewPager showsViewpager = findViewById(R.id.shows_viewpager);

        // setup the ViewPager with the sections header (in ideal state
        showsViewpager.setOffscreenPageLimit(2);
        showsViewpager.setAdapter(new ShowDetailsAdapter(getSupportFragmentManager(), onTheAir));

        showsViewpager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(showsTabLayout));
        showsTabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(showsViewpager));
        // Give the TabLayout the ViewPager
        showsTabLayout.setupWithViewPager(showsViewpager);

        showsAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onExpanded(AppBarLayout appBarLayout) {
                showsToolbar.setTitle("");
                showsFavorite.show();
            }

            @Override
            public void onCollapsed(AppBarLayout appBarLayout) {
                showsToolbar.setTitle(onTheAir.getOriginalName());
                showsFavorite.hide();
            }

            @Override
            public void onIdle(AppBarLayout appBarLayout) {
            }
        });
    }

    private void loadShowDetails() {
        MaterialTextView showsInfo = findViewById(R.id.shows_info);
        compositeDisposable.add(showsViewModel.discoverShowsDetailsAppendVideos(onTheAir.getId())
                .subscribe(
                        showsDetailsResponse -> {

                            if (showsDetailsResponse != null) {
                                String info = getString(R.string.concat_strings_placeholder_shows,
                                        showsDetailsResponse.getNumberOfSeasons(),
                                        showsDetailsResponse.getNumberOfEpisodes(),
                                        showsDetailsResponse.getPopularity(),
                                        DateTimeUtils.getNameOfMonth(
                                                showsDetailsResponse.getFirstAirDate()),
                                        showsDetailsResponse.getVoteAverage(),
                                        DateTimeUtils.getNameOfMonth(
                                                showsDetailsResponse.getLastAirDate()),
                                        showsDetailsResponse.getStatus());
                                // Make sure setSelected is specified for marqueee to work
                                showsInfo.setSelected(true);
                                showsInfo.setTextColor(
                                        getResources().getColor(R.color.darkThemePrimary));
                                showsInfo.setText(info);
                            } else {
                                showsInfo.setVisibility(View.GONE);
                            }
                        }, throwable -> MaterialDialogUtils.showDialog(this,
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))
                ));
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
        super.onBackPressed();
        Intent intent = new Intent(this, DiscoverMoviesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadShowDetails();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
