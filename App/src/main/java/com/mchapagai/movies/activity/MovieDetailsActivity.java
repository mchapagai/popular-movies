package com.mchapagai.movies.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mchapagai.library.utils.DateTimeUtils;
import com.mchapagai.library.utils.LibraryUtils;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.ItemOffsetDecoration;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.CreditsAdapter;
import com.mchapagai.movies.adapter.GenresAdapter;
import com.mchapagai.movies.adapter.ReviewsAdapter;
import com.mchapagai.movies.adapter.VideosAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.CastCredit;
import com.mchapagai.movies.model.CrewCredits;
import com.mchapagai.movies.model.Genres;
import com.mchapagai.movies.model.Movies;
import com.mchapagai.movies.model.Reviews;
import com.mchapagai.movies.model.Videos;
import com.mchapagai.movies.model.binding.CombinedCreditsResponse;
import com.mchapagai.movies.model.binding.CreditResponse;
import com.mchapagai.movies.model.binding.MovieDetailsResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.VideoResponse;
import com.mchapagai.movies.utils.MovieUtils;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivity extends BaseActivity {

    private final String TAG = MovieDetailsActivity.class.getSimpleName();

    @BindView(R.id.details_backdrop)
    MaterialImageView detailsBackdrop;
    @BindView(R.id.movie_details_toolbar)
    Toolbar movieDetailsToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.original_title)
    TextView detailsOriginalTitle;
    @BindView(R.id.movie_tagline)
    MaterialTextView movieTagline;
    @BindView(R.id.release_date)
    MaterialTextView detailsReleaseDate;
    @BindView(R.id.ratings)
    MaterialTextView detailsRatings;
    @BindView(R.id.details_over_view)
    MaterialTextView detailsOverView;
    @BindView(R.id.movie_genre_recycler_view)
    RecyclerView movieGenreRecyclerView;
    @BindView(R.id.movie_creditsrecycler_view)
    RecyclerView movieCreditsrecyclerView;
    @BindView(R.id.video_divider)
    View videoDivider;
    @BindView(R.id.videos_title)
    TextView videosTitle;
    @BindView(R.id.movie_video_recycler_view)
    RecyclerView movieVideoRecyclerView;
    @BindView(R.id.videos_error_text)
    MaterialTextView videosErrorText;
    @BindView(R.id.details_empty_video)
    MaterialImageView detailsEmptyVideo;
    @BindView(R.id.video_layout)
    ConstraintLayout videoLayout;
    @BindView(R.id.reviews_divider)
    View reviewsDivider;
    @BindView(R.id.detail_review_header)
    TextView detailReviewHeader;
    @BindView(R.id.reviews_recycler_view)
    RecyclerView reviewsRecyclerView;
    @BindView(R.id.details_favorite)
    FloatingActionButton favoriteActionButton;

    private Movies movies;
    private VideosAdapter videosAdapter;
    private List<Videos> videoItems = new ArrayList<>();
    private List<Genres> genreItems = new ArrayList<>();
    private List<CastCredit> castCredits = new ArrayList<>();
    private List<CrewCredits> crewCredits = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity_container);
        ButterKnife.bind(this);

        movies = getIntent().getParcelableExtra(Constants.MOVIE_DETAILS);

        // Bottom slide animation
        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        postponeEnterTransition();

        initViews();
        populateMovieTrailer();
        populateMovieReviews();
    }

    @TargetApi(VERSION_CODES.M)
    private void initViews() {
        // Fullscreen - hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(movieDetailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_close);
        movieDetailsToolbar.getNavigationIcon().setTint(
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
        if (!TextUtils.isEmpty(movies.getReleaseDate())) {
            detailsReleaseDate.setText(DateTimeUtils.getNameOfMonth(movies.getReleaseDate()));
        }
        detailsRatings.setText(getString(R.string.details_rating_votes_count,
                String.format(Locale.US, "%.2f", movies.getVoteAverage()),
                String.valueOf(movies.getVoteCount())));
        Uri backdropUri = MovieUtils.getMovieBackdropPathUri(movies);

        movieGenreRecyclerView.setLayoutManager(
                new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL,
                        false));
        final GenresAdapter genresAdapter = new GenresAdapter(genreItems);
        movieGenreRecyclerView.setAdapter(genresAdapter);
        Picasso.get().load(backdropUri).into(detailsBackdrop);

        favoriteActionButton.setOnClickListener(
                v -> LibraryUtils
                        .showSnackBar(this, v,
                                getString(R.string.prompt_to_login_message)));
    }

    private void loadMoreMovies() {
        compositeDisposable.add(movieViewModel.getMovieDetails(movies.getId())
                .subscribe(this::movieDetailsResponseItems,
                        throwable -> MaterialDialogUtils.showDialog(this,
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))

                ));
    }

    private void movieDetailsResponseItems(MovieDetailsResponse response) {
        movieGenreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        movieGenreRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        genreItems = response.getGenres();
        if (response.getTagline() != null && !response.getTagline().equals("")) {
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
            Videos video = videosAdapter.getItem(position);
            if (video != null && video.isYoutubeVideo()) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });
        movieVideoRecyclerView.setAdapter(videosAdapter);
    }

    private void creditResponseItems(CreditResponse response) {
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
            intent.putExtra(Constants.PERSON_ID_INTENT, combinedCreditsResponse.getId());
            startActivity(intent);
        });
    }

    private ArrayList<CombinedCreditsResponse> combinedCreditsResponse() {
        ArrayList<CombinedCreditsResponse> combinedCreditsResponseList = new ArrayList<>();
        for (CastCredit castCredit : castCredits) {
            CombinedCreditsResponse combinedCreditsResponse = new CombinedCreditsResponse();
            combinedCreditsResponse.setName(castCredit.getName());
            combinedCreditsResponse.setDescription(castCredit.getCharacter());
            combinedCreditsResponse.setProfileImagePath(castCredit.getProfilePath());
            combinedCreditsResponse.setId(castCredit.getId());
            combinedCreditsResponseList.add(combinedCreditsResponse);
        }

        for (CrewCredits crewCredit : crewCredits) {
            CombinedCreditsResponse combinedCreditsResponse = new CombinedCreditsResponse();
            combinedCreditsResponse.setName(crewCredit.getName());
            combinedCreditsResponse.setDescription(crewCredit.getJob());
            combinedCreditsResponse.setProfileImagePath(crewCredit.getProfilePath());
            combinedCreditsResponse.setId(crewCredit.getId());
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

        Observable<CreditResponse> movieCreditObservable = movieViewModel.getMovieCreditDetails(
                movies.getId());
        Observable<VideoResponse> movieVideoObservable = movieViewModel.getMovieVideosbyId(
                movies.getId());

        compositeDisposable.add(
                Observable.merge(movieCreditObservable, movieVideoObservable).subscribe(
                        response -> {

                            if (response instanceof CreditResponse) {
                                creditResponseItems((CreditResponse) response);
                            } else {
                                VideoResponse videoResponse = (VideoResponse) response;
                                if (videoResponse.getVideos().isEmpty()
                                        || videoResponse.getVideos().size() == 0) {
                                    showVideoError();
                                } else {
                                    hideVideoError();
                                    videosAdapter.setMovieVideos(videoResponse.getVideos());
                                }
                            }
                        }));
    }

    private void loadMovieReviews() {
        compositeDisposable.add(movieViewModel.getMovieReviewsById(movies.getId())
                .subscribe(
                        response -> reviewsResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(this,
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
