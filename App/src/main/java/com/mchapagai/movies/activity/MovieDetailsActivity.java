package com.mchapagai.movies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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
import com.mchapagai.movies.model.VideoItems;
import com.mchapagai.movies.model.binding.CombinedCreditsResponse;
import com.mchapagai.movies.model.binding.CreditResponse;
import com.mchapagai.movies.model.binding.MovieDetailsResponse;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.model.binding.VideoResponse;
import com.mchapagai.movies.utils.DateTimeUtils;
import com.mchapagai.movies.utils.MovieUtils;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivity extends BaseActivity {

    private Movies movies;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private MaterialImageView posterBackdropImageView;
    private TextView originalTitle, releaseDate, ratings, overview;
    private RecyclerView reviewsRecyclerView, videosRecyclerView, genreRecycleView, creditsRecyclerView;
    private View videoDivider, reviewsDivider;
    private TextView videoTitle;
    private MaterialTextView videoErrorText;
    private TextView reviewHeader;
    private MaterialImageView videoEmptyStateImage;
    private VideosAdapter videosAdapter;
    private List<VideoItems> videoItems = new ArrayList<>();
    private List<Genres> genreItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private GenresAdapter genresAdapter;
    private List<CastCredit> castCredits = new ArrayList<>();
    private List<CrewCredits> crewCredits = new ArrayList<>();
    private CreditsAdapter adapter;
    private TextView tagline;

    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity_container);

        movies = getIntent().getParcelableExtra(Constants.MOVIE_DETAILS);

        // Bottom slide animation
        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        postponeEnterTransition();

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
        tagline = findViewById(R.id.movie_tagline);

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
        releaseDate.setText(DateTimeUtils.getNameOfMonth(movies.getReleaseDate()));
        ratings.setText(String.format(Locale.US, "%.2f", movies.getVoteAverage()));
        Uri backdropUri = MovieUtils.getMovieBackdropPathUri(movies);

        genreRecycleView = findViewById(R.id.movie_genre_recycler_view);
        genreRecycleView.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        genresAdapter = new GenresAdapter(genreItems);
        genreRecycleView.setAdapter(genresAdapter);

        Picasso.get().load(backdropUri).into(posterBackdropImageView);
    }

    private void loadMoreMovies() {
        compositeDisposable.add(movieViewModel.getMovieDetails(movies.getId())
                .subscribe(response -> movieDetailsResponseItems(response),
                        throwable -> {
                        }
                ));
    }

    private void movieDetailsResponseItems(MovieDetailsResponse response) {
        genreRecycleView = findViewById(R.id.movie_genre_recycler_view);
        genreRecycleView.setItemAnimator(new DefaultItemAnimator());
        genreRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        genreItems = response.getGenres();
        if (response.getTagline() != null && !response.getTagline().equals("")) {
            tagline.setText(response.getTagline());
        }
        genreRecycleView.setAdapter(new GenresAdapter(genreItems));
    }

    private void populateMovieTrailer() {
        videoDivider = findViewById(R.id.video_divider);
        videoTitle = findViewById(R.id.videos_title);
        videoErrorText = findViewById(R.id.videos_error_text);
        videosRecyclerView = findViewById(R.id.movie_video_recycler_view);
        videoEmptyStateImage = findViewById(R.id.details_empty_video);
        videoTitle = findViewById(R.id.videos_title);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        videosRecyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.margin_4dp));
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

    private void creditResponseItems(CreditResponse response) {
        creditsRecyclerView = findViewById(R.id.movie_creditsrecycler_view);
        creditsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        creditsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castCredits = response.getCast();
        crewCredits = response.getCrew();

        adapter = new CreditsAdapter(combineCreditsForUI());
        creditsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(combinedCreditsResponse -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), CreditDetailsActivity.class);
            intent.putExtra(Constants.PERSON_ID_INTENT, combinedCreditsResponse.getId());
            startActivity(intent);
        });
    }

    private ArrayList<CombinedCreditsResponse> combineCreditsForUI() {
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
        reviewHeader = findViewById(R.id.detail_review_header);
        reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);
        reviewsDivider = findViewById(R.id.reviews_divider);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadMovieCredit() {

        Observable<CreditResponse> movieCreditObservable = movieViewModel.getMovieCreditDetails(movies.getId());
        Observable<VideoResponse> movieVideoObservable = movieViewModel.getMovieVideosbyId(movies.getId());

        compositeDisposable.add(Observable.merge(movieCreditObservable, movieVideoObservable).subscribe(response -> {

            if (response instanceof CreditResponse) {
                creditResponseItems((CreditResponse) response);
            } else {
                VideoResponse videoResponse = (VideoResponse) response;
                if (videoResponse.getVideos().isEmpty() || videoResponse.getVideos().size() == 0) {
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
        videoTitle.setVisibility(View.GONE);
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
