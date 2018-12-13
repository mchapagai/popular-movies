package com.example.mchapagai.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.library.utils.MaterialDialogUtils;
import com.example.library.views.MaterialImageView;
import com.example.mchapagai.R;
import com.example.mchapagai.adapter.ReviewsAdapter;
import com.example.mchapagai.adapter.VideosAdapter;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.Movies;
import com.example.mchapagai.model.Reviews;
import com.example.mchapagai.model.VideoItems;
import com.example.mchapagai.model.binding.ReviewsResponse;
import com.example.mchapagai.utils.DateTImeUtils;
import com.example.mchapagai.utils.MovieUtils;
import com.example.mchapagai.utils.RoundedTransformation;
import com.example.mchapagai.view_model.MovieViewModel;
import com.example.mchapagai.widget.ItemOffsetDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivity extends BaseActivity {

    private Movies movies;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private MaterialImageView posterBackdropImageView;
    private TextView originalTitle, releaseDate, ratings, overview;
    private RecyclerView reviewsRecyclerView, videosRecyclerView;
    private View videoDivider, reviewsDivider;
    private TextView videoTitle, videoErrorText, reviewHeader, emptyStateText;
    private MaterialImageView tmdbLogo, videoEmptyStateImage;
    private VideosAdapter videosAdapter;
    private List<VideoItems> videoItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity_container);

        movies = getIntent().getParcelableExtra(Constants.MOVIE_DETAILS);

        initViews();
        populateMovieTrailer();
        populateMovieReviews();
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
        releaseDate.setText(DateTImeUtils.getNameOfMonth(movies.getReleaseDate()));
        ratings.setText(String.format(Locale.US, "%.2f", movies.getVoteAverage()));
        Uri backdropUri = MovieUtils.getMovieBackdropPathUri(movies);
        Picasso.get().load(backdropUri)
                .transform(new RoundedTransformation(20, 0))
                .into(posterBackdropImageView);
    }

    private void populateMovieTrailer() {
        videoDivider = findViewById(R.id.video_divider);
        videoTitle = findViewById(R.id.videos_title);
        videoErrorText = findViewById(R.id.videos_error_text);
        videosRecyclerView = findViewById(R.id.movie_video_recycler_view);
        videoEmptyStateImage = findViewById(R.id.details_empty_video);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        videosRecyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.movie_item_offset));
        videosRecyclerView.setLayoutManager(manager);
        videosRecyclerView.setHasFixedSize(true);
        videosRecyclerView.setItemAnimator(new DefaultItemAnimator());

        videosAdapter = new VideosAdapter(this, videoItems);
        videosAdapter.setOnItemClick((view, position) -> {
            VideoItems video = videosAdapter.getItem(position);
            if (video != null && video.isYoutubeVideo()) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });
        videosRecyclerView.setAdapter(videosAdapter);
    }

    private void populateMovieReviews() {
        reviewHeader = findViewById(R.id.detail_review_header);
        reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);
        reviewsDivider = findViewById(R.id.reviews_divider);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadMovieVideos() {
        compositeDisposable.add(movieViewModel.getMovieVideosbyId(movies.getId())
                .subscribe(response -> {
                    if (response.getVideos().isEmpty() || response.getVideos().size() == 0) {
                        showVideoError();
                    } else {
                        hideVideoError();
                        videosAdapter.setMovieVideos(response.getVideos());
                    }
                }, throwable -> MaterialDialogUtils.showDialog(MovieDetailsActivity.this,
                                   getResources().getString(R.string.service_error_title),
                                   throwable.getMessage(),
                                   getResources().getString(R.string.material_dialog_ok))

                ));
    }

    private void loadMovieReviews() {
        compositeDisposable.add(movieViewModel.getMovieReviewsById(movies.getId())
                .subscribe(
                        response -> reviewsResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(MovieDetailsActivity.this,
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))
                ));
    }

    private void reviewsResponseItems(ReviewsResponse response) {

        List<Reviews> reviewItems = response.getReviews();
        if (reviewItems.isEmpty()) {
            reviewsRecyclerView.setVisibility(View.GONE);
            reviewsDivider.setVisibility(View.GONE);
            reviewHeader.setVisibility(View.GONE);
        } else {
            reviewsRecyclerView.setVisibility(View.VISIBLE);
            reviewsDivider.setVisibility(View.VISIBLE);
            reviewHeader.setVisibility(View.VISIBLE);
        }
        reviewsRecyclerView.setAdapter(new ReviewsAdapter(reviewItems));
    }

    private void showVideoError() {
        videoEmptyStateImage.setVisibility(View.VISIBLE);
        videoErrorText.setVisibility(View.VISIBLE);
        videosRecyclerView.setVisibility(View.GONE);
    }

    private void hideVideoError() {
        videoEmptyStateImage.setVisibility(View.GONE);
        videoErrorText.setVisibility(View.GONE);
        videosRecyclerView.setVisibility(View.VISIBLE);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadMovieVideos();
        loadMovieReviews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}
