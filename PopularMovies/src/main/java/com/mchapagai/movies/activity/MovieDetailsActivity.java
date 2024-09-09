package com.mchapagai.movies.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mchapagai.core.model.MovieCombinedCreditModel;
import com.mchapagai.core.response.common.GenresResponse;
import com.mchapagai.core.response.common.ReviewListResponse;
import com.mchapagai.core.response.common.ReviewResponse;
import com.mchapagai.core.response.common.VideoListResponse;
import com.mchapagai.core.response.common.VideoResponse;
import com.mchapagai.core.response.movies.MovieCastResponse;
import com.mchapagai.core.response.movies.MovieCreditResponse;
import com.mchapagai.core.response.movies.MovieCrewResponse;
import com.mchapagai.core.response.movies.MovieDetailsResponse;
import com.mchapagai.movies.utils.LibraryUtils;
import com.mchapagai.movies.utils.MaterialDialogUtils;
import com.mchapagai.movies.views.ItemOffsetDecoration;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.CreditsAdapter;
import com.mchapagai.movies.adapter.GenresAdapter;
import com.mchapagai.movies.adapter.ReviewsAdapter;
import com.mchapagai.movies.adapter.VideosAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivity extends BaseActivity {

    ImageView detailsBackdrop;
    Toolbar movieDetailsToolbar;
    CollapsingToolbarLayout collapsingToolbar;
    AppBarLayout appbar;
    TextView detailsOriginalTitle;
    TextView movieTagline;
    TextView detailsReleaseDate;
    TextView detailsRatings;
    TextView detailsOverView;
    RecyclerView movieGenreRecyclerView;
    RecyclerView movieCreditsrecyclerView;
    View videoDivider;
    TextView videosTitle;
    RecyclerView movieVideoRecyclerView;
    TextView videosErrorText;
    ImageView detailsEmptyVideo;
    ConstraintLayout videoLayout;
    View reviewsDivider;
    TextView detailReviewHeader;
    RecyclerView reviewsRecyclerView;
    FloatingActionButton favoriteActionButton;

    private int movieId;
    private VideosAdapter videosAdapter;
    private final List<VideoResponse> videoItems = new ArrayList<>();
    private List<GenresResponse> genreItems = new ArrayList<>();
    private List<MovieCastResponse> castCredits = new ArrayList<>();
    private List<MovieCrewResponse> crewCredits = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity_container);

        movieId = getIntent().getIntExtra(Constants.MOVIE_DETAILS, -1);

        detailsBackdrop = findViewById(R.id.details_backdrop);
        movieDetailsToolbar = findViewById(R.id.movie_details_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appbar = findViewById(R.id.appbar);
        detailsOriginalTitle = findViewById(R.id.original_title);
        movieTagline = findViewById(R.id.movie_tagline);
        detailsReleaseDate = findViewById(R.id.release_date);
        detailsRatings = findViewById(R.id.ratings);
        detailsOverView = findViewById(R.id.details_over_view);
        movieGenreRecyclerView = findViewById(R.id.movie_genre_recycler_view);
        movieCreditsrecyclerView = findViewById(R.id.movie_creditsrecycler_view);
        videoDivider = findViewById(R.id.video_divider);
        videosTitle = findViewById(R.id.videos_title);
        movieVideoRecyclerView = findViewById(R.id.movie_video_recycler_view);
        videosErrorText = findViewById(R.id.videos_error_text);
        detailsEmptyVideo = findViewById(R.id.details_empty_video);
        videoLayout = findViewById(R.id.video_layout);
        reviewsDivider = findViewById(R.id.reviews_divider);
        detailReviewHeader = findViewById(R.id.detail_review_header);
        reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);
        favoriteActionButton = findViewById(R.id.details_favorite);

        // Bottom slide animation
        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        postponeEnterTransition();

//        initViews();
        populateMovieTrailer();
        populateMovieReviews();
        loadMoreMovies();
    }

    @TargetApi(VERSION_CODES.M)
    private void initViews(MovieDetailsResponse movies) {
        // Fullscreen - hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(movieDetailsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_white);
        Objects.requireNonNull(movieDetailsToolbar.getNavigationIcon()).setTint(
                getResources().getColor(R.color.darkThemePrimaryDark));
        movieDetailsToolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.white), Mode.DARKEN);
        setTitle("");

        // Implement addOnOffsetChangedListener to show CollapsingToolbarLayout Tile only when
        // collapsed
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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

        detailsOriginalTitle.setText(movies.getTitle());
        detailsOverView.setText(movies.getOverview());
        detailsReleaseDate.setText(movies.getFormattedReleaseDate());
        detailsRatings.setText(getString(R.string.details_rating_votes_count,
                String.format(Locale.US, "%.2f", movies.getVoteAverage()),
                String.valueOf(movies.getVoteCount())));
        Uri posterUrl = Uri.parse(movies.getFullBackdropPath());

        movieGenreRecyclerView.setLayoutManager(
                new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL,
                        false));
        final GenresAdapter genresAdapter = new GenresAdapter(genreItems);
        movieGenreRecyclerView.setAdapter(genresAdapter);
        Picasso.get().load(posterUrl).into(detailsBackdrop);

        favoriteActionButton.setOnClickListener(
                v -> LibraryUtils
                        .showSnackBar(this, v,
                                getString(R.string.prompt_to_login_message)));
    }

    private void loadMoreMovies() {
        compositeDisposable.add(movieViewModel.getMovieDetails(movieId)
                .subscribe(this::movieDetailsResponseItems,
                        throwable -> MaterialDialogUtils.showDialog(this,
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))

                ));
    }

    private void movieDetailsResponseItems(MovieDetailsResponse response) {
        initViews(response);
        movieGenreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        movieGenreRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        genreItems = response.getGenres();
        if (!response.getTagline().isEmpty()) {
            movieTagline.setText(response.getTagline());
        } else {
            movieTagline.setVisibility(View.GONE);
        }
        movieGenreRecyclerView.setAdapter(new GenresAdapter(genreItems));
    }

    private void populateMovieTrailer() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        movieVideoRecyclerView.addItemDecoration(
                new ItemOffsetDecoration(this, R.dimen.margin_4dp));
        movieVideoRecyclerView.setLayoutManager(manager);
        movieVideoRecyclerView.setHasFixedSize(true);
        movieVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());

        videosAdapter = new VideosAdapter(this, videoItems);
        videosAdapter.setOnItemClick((view, position) -> {
            VideoResponse video = videosAdapter.getItem(position);
            if (video != null && video.isYoutubeVideo()) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });
        movieVideoRecyclerView.setAdapter(videosAdapter);
    }

    private void creditResponseItems(MovieCreditResponse response) {
        movieCreditsrecyclerView.setItemAnimator(new DefaultItemAnimator());
        movieCreditsrecyclerView
                .setLayoutManager(
                        new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castCredits = response.getCast();
        crewCredits = response.getCrew();
        final CreditsAdapter adapter = new CreditsAdapter(combinedCreditsResponse());
        movieCreditsrecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(combinedCreditsResponse -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), CreditDetailsActivity.class);
            intent.putExtra(Constants.PERSON_ID_INTENT, combinedCreditsResponse.getCreditId());
            startActivity(intent);
        });
    }

    private ArrayList<MovieCombinedCreditModel> combinedCreditsResponse() {
        ArrayList<MovieCombinedCreditModel> combinedCreditsResponseList = new ArrayList<>();
        for (MovieCastResponse castCredit : castCredits) {
            MovieCombinedCreditModel combinedCreditsResponse = new MovieCombinedCreditModel(
                    castCredit.getId(),
                    Objects.requireNonNull(castCredit.getName()),
                    Objects.requireNonNull(castCredit.getCharacter()),
                    castCredit.getProfilePath()
            );
            combinedCreditsResponseList.add(combinedCreditsResponse);
        }

        for (MovieCrewResponse crewCredit : crewCredits) {
            MovieCombinedCreditModel combinedCreditsResponse = new MovieCombinedCreditModel(
                    crewCredit.getId(),
                    Objects.requireNonNull(crewCredit.getName()),
                    Objects.requireNonNull(crewCredit.getJob()),
                    crewCredit.getProfilePath()
            );
            combinedCreditsResponseList.add(combinedCreditsResponse);
        }

        return combinedCreditsResponseList;
    }

    private void populateMovieReviews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadMovieCredit() {

        Observable<MovieCreditResponse> movieCreditObservable = movieViewModel.getMovieCreditDetails(
                movieId);
        Observable<VideoListResponse> movieVideoObservable = movieViewModel.getMovieVideosbyId(
                movieId);

        compositeDisposable.add(
                Observable.merge(movieCreditObservable, movieVideoObservable).subscribe(
                        response -> {

                            if (response instanceof MovieCreditResponse) {
                                creditResponseItems((MovieCreditResponse) response);
                            } else {
                                VideoListResponse videoResponse = (VideoListResponse) response;
                                if (videoResponse.getVideoList().isEmpty()
                                        || videoResponse.getVideoList().size() == 0) {
                                    showVideoError();
                                } else {
                                    hideVideoError();
                                    videosAdapter.setMovieVideos(videoResponse.getVideoList());
                                }
                            }
                        }));
    }

    private void loadMovieReviews() {
        compositeDisposable.add(movieViewModel.getMovieReviewsById(movieId)
                .subscribe(
                        response -> reviewsResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(this,
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))
                ));
    }

    private void reviewsResponseItems(ReviewListResponse response) {
        List<ReviewResponse> reviewItems = response.getReviewList();
        if (reviewItems.isEmpty()) {
            reviewsRecyclerView.setVisibility(View.GONE);
            reviewsDivider.setVisibility(View.GONE);
            detailReviewHeader.setVisibility(View.GONE);
        } else {
            reviewsRecyclerView.setVisibility(View.VISIBLE);
            reviewsDivider.setVisibility(View.VISIBLE);
            detailReviewHeader.setVisibility(View.VISIBLE);
        }
        reviewsRecyclerView.setAdapter(new ReviewsAdapter(reviewItems));
    }

    private void showVideoError() {
        detailsEmptyVideo.setVisibility(View.VISIBLE);
        videosErrorText.setVisibility(View.VISIBLE);
        movieVideoRecyclerView.setVisibility(View.GONE);
        videosTitle.setVisibility(View.GONE);
    }

    private void hideVideoError() {
        detailsEmptyVideo.setVisibility(View.GONE);
        videosErrorText.setVisibility(View.GONE);
        movieVideoRecyclerView.setVisibility(View.VISIBLE);
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
        loadMovieCredit();
        loadMovieReviews();
        loadMoreMovies();
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
