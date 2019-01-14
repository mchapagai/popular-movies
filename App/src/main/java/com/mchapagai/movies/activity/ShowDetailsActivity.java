package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mchapagai.library.utils.DateTimeUtils;
import com.mchapagai.library.utils.LibraryUtils;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.ShowDetailsAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import com.mchapagai.movies.view_model.ShowsViewModel;
import com.mchapagai.movies.widget.AppBarStateChangeListener;
import com.squareup.picasso.Picasso;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ShowDetailsActivity extends BaseActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.show_details_backdrop)
    MaterialImageView showDetailsBackdrop;

    @BindView(R.id.shows_appbar)
    AppBarLayout showsAppbar;

    @BindView(R.id.shows_collapsing_toolbar)
    CollapsingToolbarLayout showsCollapsingToolbar;

    @BindView(R.id.shows_favorite)
    FloatingActionButton showsFavorite;

    @BindView(R.id.shows_tab_layout)
    TabLayout showsTabLayout;

    @BindView(R.id.shows_title)
    MaterialTextView showsTitle;

    @BindView(R.id.shows_info)
    MaterialTextView showsInfo;

    @BindView(R.id.shows_toolbar)
    Toolbar showsToolbar;

    @BindView(R.id.shows_viewpager)
    ViewPager showsViewpager;

    private OnTheAir onTheAir;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ShowsViewModel showsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shows_details_activity_container);
        ButterKnife.bind(this);
        onTheAir = getIntent().getParcelableExtra(Constants.SHOWS_DETAILS);

        // Bottom slide animation
        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        postponeEnterTransition();

        initViews();
        setupTablayout();
    }

    private void initViews() {
        // Fullscreen - hide the status bar
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(showsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        showsTitle.setText(onTheAir.getName());
        Picasso.get().load(Constants.SECURE_BASE_URL + onTheAir.getBackdropPath()).into(showDetailsBackdrop);

        showsFavorite.setOnClickListener(
                v -> LibraryUtils
                        .showSnackBar(ShowDetailsActivity.this, v, getString(R.string.prompt_to_login_message)));
    }

    private void setupTablayout() {
        // setup the ViewPager with the sections header (in ideal state
        showsViewpager.setOffscreenPageLimit(2);
        showsViewpager.setAdapter(new ShowDetailsAdapter(getSupportFragmentManager(), onTheAir));

        showsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(showsTabLayout));
        showsTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(showsViewpager));
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
        compositeDisposable.add(showsViewModel.discoverShowsDetailsAppendVideos(onTheAir.getId())
                .subscribe(
                        showsDetailsResponse -> {

                            if (showsDetailsResponse != null) {
                                String info = getString(R.string.concat_strings_placeholder_shows,
                                        showsDetailsResponse.getNumberOfSeasons(),
                                        showsDetailsResponse.getNumberOfEpisodes(),
                                        showsDetailsResponse.getPopularity(),
                                        DateTimeUtils.getNameOfMonth(showsDetailsResponse.getFirstAirDate()),
                                        showsDetailsResponse.getVoteAverage(),
                                        DateTimeUtils.getNameOfMonth(showsDetailsResponse.getLastAirDate()),
                                        showsDetailsResponse.getStatus());
                                // Make sure setSelected is specified for marqueee to work
                                showsInfo.setSelected(true);
                                showsInfo.setTextColor(getResources().getColor(R.color.darkThemePrimary));
                                showsInfo.setText(info);
                            } else {
                                showsInfo.setVisibility(View.GONE);
                            }
                        }, throwable -> MaterialDialogUtils.showDialog(ShowDetailsActivity.this,
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
