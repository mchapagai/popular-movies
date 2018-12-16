package com.example.mchapagai.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
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
import com.example.mchapagai.adapter.CreditsAdapter;
import com.example.mchapagai.adapter.GenresAdapter;
import com.example.mchapagai.adapter.ReviewsAdapter;
import com.example.mchapagai.adapter.VideosAdapter;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.*;
import com.example.mchapagai.model.binding.CreditResponse;
import com.example.mchapagai.model.binding.MovieDetailsResponse;
import com.example.mchapagai.model.binding.ReviewsResponse;
import com.example.mchapagai.utils.DateTImeUtils;
import com.example.mchapagai.utils.MovieUtils;
import com.example.mchapagai.view_model.MovieViewModel;
import com.example.mchapagai.widget.ItemOffsetDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
    private TextView videoErrorText;
    private TextView reviewHeader;
    private MaterialImageView videoEmptyStateImage;
    private VideosAdapter videosAdapter;
    private List<VideoItems> videoItems = new ArrayList<>();
    private List<Genres> genreItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CoordinatorLayout layout;
    private GenresAdapter genresAdapter;
    private CreditsAdapter creditsAdapter;
    private List<CastCredit> castCredits = new ArrayList<>();
    private List<CrewCredits> crewCredits = new ArrayList<>();

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
        layout = findViewById(R.id.main_content);

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

        genreRecycleView = findViewById(R.id.movie_genre_recycler_view);
        genreRecycleView.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        genresAdapter = new GenresAdapter(genreItems);
        genreRecycleView.setAdapter(genresAdapter);


        Picasso.get().load(backdropUri)
                .into(new com.squareup.picasso.Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        posterBackdropImageView.setImageBitmap(bitmap);

                        Palette.from(bitmap).generate(palette -> {
                            genreRecycleView.setBackground(getGradientDrawable(getTopColor(palette), getCenterLightColor(palette), getBottomDarkColor(palette)));

                            Palette.Swatch vibrant = palette.getVibrantSwatch();
                            if (vibrant != null) {
//                                releaseDate.setTextColor(vibrant.getBodyTextColor());
                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    private void loadMoreMovies() {
        compositeDisposable.add(movieViewModel.getMovieDetails(movies.getId())
                .subscribe(new Consumer<MovieDetailsResponse>() {
                            @Override
                            public void accept(MovieDetailsResponse response) throws Exception {
                                MovieDetailsActivity.this.movieDetailsResponseItems(response);
                            }
                        },
                        throwable -> {}
                ));
    }

    private void movieDetailsResponseItems(MovieDetailsResponse response) {
        genreRecycleView = findViewById(R.id.movie_genre_recycler_view);
        genreRecycleView.setItemAnimator(new DefaultItemAnimator());
        genreRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        genreItems = response.getGenres();
        genreRecycleView.setAdapter(new GenresAdapter(genreItems));
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

    private void creditResponseItems(CreditResponse response) {
        creditsRecyclerView = findViewById(R.id.movie_creditsrecycler_view);
        creditsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        creditsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castCredits = response.getCast();
        crewCredits = response.getCrew();
        creditsRecyclerView.setAdapter(new CreditsAdapter(castCredits, crewCredits, "Crew"));
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


    private void loadMovieCredits() {
        compositeDisposable.add(movieViewModel.getMovieCreditDetails(movies.getId())
                .subscribe(
                        new Consumer<CreditResponse>() {
                            @Override
                            public void accept(CreditResponse response) throws Exception {
                                creditResponseItems(response);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }
                ));
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
        loadMoreMovies();
        loadMovieCredits();
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


    /*Creating gradient drawable to be used as a background using three colors - top color ,center light color and bottom dark color */
    private GradientDrawable getGradientDrawable(int topColor, int centerColor, int bottomColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TL_BR);
        gradientDrawable.setShape(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setColors(new int[]{
                topColor,
                centerColor,
                bottomColor
        });
        return gradientDrawable;
    }

    /**
     * @param palette generated palette from image
     * @return return top color for gradient either muted or vibrant whatever is available
     */
    private int getTopColor(Palette palette) {
        if (palette.getMutedSwatch() != null || palette.getVibrantSwatch() != null)
            return palette.getMutedSwatch() != null ? palette.getMutedSwatch().getRgb() : palette.getVibrantSwatch().getRgb();
        else return Color.RED;
    }

    /**
     * @param palette generated palette from image
     * @return return center light color for gradient either muted or vibrant whatever is available
     */
    private int getCenterLightColor(Palette palette) {
        if (palette.getLightMutedSwatch() != null || palette.getLightVibrantSwatch() != null)
            return palette.getLightMutedSwatch() != null ? palette.getLightMutedSwatch().getRgb() : palette.getLightVibrantSwatch().getRgb();
        else return Color.GREEN;
    }

    /**
     * @param palette generated palette from image
     * @return return bottom dark color for gradient either muted or vibrant whatever is available
     */
    private int getBottomDarkColor(Palette palette) {
        if (palette.getDarkMutedSwatch() != null || palette.getDarkVibrantSwatch() != null)
            return palette.getDarkMutedSwatch() != null ? palette.getDarkMutedSwatch().getRgb() : palette.getDarkVibrantSwatch().getRgb();
        else return Color.BLUE;
    }
}
