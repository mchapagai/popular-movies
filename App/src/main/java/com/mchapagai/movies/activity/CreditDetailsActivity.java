package com.mchapagai.movies.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mchapagai.library.common.LibraryConstants;
import com.mchapagai.library.utils.AnimationUtils;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.utils.PaletteColorUtils;
import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.PersonDetailsAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.CombinedCastCredit;
import com.mchapagai.movies.model.CombinedCrewCredits;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.binding.CreditResponseCombined;
import com.mchapagai.movies.model.binding.PersonResponse;
import com.mchapagai.movies.utils.DateTimeUtils;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;

public class CreditDetailsActivity extends BaseActivity {

    private static final String TAG = CreditDetailsActivity.class.getSimpleName();

    @Inject
    MovieViewModel movieViewModel;

    @BindView(R.id.credit_app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.credit_collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.credit_toolbar)
    Toolbar toolbar;
    @BindView(R.id.credit_circle_image_view)
    MaterialCircleImageView profileImageView;
    @BindView((R.id.credits_person_name))
    TextView nameView;
    @BindView(R.id.credits_biography)
    MaterialTextView biographyView;
    @BindView(R.id.birth_date)
    MaterialTextView birthday;

    private PersonResponse personResponse;
    private List<CombinedCastCredit> castCredits = new ArrayList<>();
    private List<CombinedCrewCredits> crewCredits = new ArrayList<>();
    private RecyclerView personRecyclerView, crewRecyclerView;
    private PersonDetailsAdapter personDetailsAdapter;
    private ViewStub creditViewStub, crewViewStub;
    private int statusBarColor;
    private final Handler handler = new Handler();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.person_details_container_layout);
        
        // Fullscreen - hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                    if (personResponse != null) {
                        collapsingToolbarLayout.setTitle(personResponse.getName());
                    }
                    isShown = true;
                } else if (isShown) {
                    // There should be a space between double quote otherwise it won't work
                    collapsingToolbarLayout.setTitle(" ");
                    isShown = false;
                }
            }
        });


        Intent i = getIntent();
        int personId = i.getIntExtra(Constants.PERSON_ID_INTENT, 0);
        Log.i(TAG, "PersonId: " + personId);

        compositeDisposable.add(movieViewModel.getPersonDetailsById(personId)
                .subscribe(
                        response -> {
                            if (response != null) {
                                personResponse = response;

                                displayImage(personResponse.getProfilePath());
                                nameView.setText(String.format("%s \"%s\"", response.getName(), response.getKnownForDepartment()));
                                biographyView.setText(response.getBiography());
                                String birthDayAndPlace = getString(R.string.concat_strings_placeholder,
                                        DateTimeUtils.getNameOfMonth(response.getBirthday()), response.getPlaceOfBirth());
                                birthday.setText(birthDayAndPlace);
                            }
                        }, throwable -> MaterialDialogUtils.showDialog(CreditDetailsActivity.this,
                                R.string.service_error_title, R.string.service_error_title, R.string.material_dialog_ok)
                ));

        compositeDisposable.add(movieViewModel.getPersonCombinedDetailsById(personId)
                .subscribe(
                        creditResponseCombined -> creditResponseItems(creditResponseCombined),
                        throwable -> {
                        }
                ));

    }

    private void displayImage(String profileImagePath) {

        Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + profileImagePath).into(profileImageView);

        final Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            boolean isDark;
            @PaletteColorUtils.ColorUtils int lightness = PaletteColorUtils.isDark(palette);
            if (lightness == LibraryConstants.UNKNOWN) {
                isDark = PaletteColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
            } else {
                isDark = lightness == LibraryConstants.DARK_COLOR;
            }

            if (!isDark) {
                // Make back icon dark on light images
                ImageButton backButton = (ImageButton) toolbar.getChildAt(0);
                backButton.setColorFilter(ContextCompat.getColor(CreditDetailsActivity.this, R.color.darkThemePrimary));

                // Make toolbar title text color dark
                collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(CreditDetailsActivity.this, R.color.darkThemeSecondary));
            }

            // color the status bar. Set a complementary dark color on L,
            // light or dark color on M (with matching status bar icons)
            statusBarColor = getWindow().getStatusBarColor();
            final Palette.Swatch topColor = PaletteColorUtils.getMostPopulousSwatch(palette);
            if (topColor != null && isDark) {
                statusBarColor = PaletteColorUtils.scrimify(topColor.getRgb(), isDark, Constants.SCRIM_ADJUSTMENT);
                // set a light status bar on M+
                if (!isDark) {
                    AnimationUtils.setLightStatusBar(getWindow().getDecorView());
                }
            }

            if (statusBarColor != getWindow().getStatusBarColor()) {
                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(getWindow().getStatusBarColor(), statusBarColor);
                statusBarColorAnim.addUpdateListener(animation -> getWindow().setStatusBarColor((int) animation.getAnimatedValue()));
                statusBarColorAnim.setDuration(500L);
                statusBarColorAnim.setInterpolator(AnimationUtils.getFastOutSlowInInterpolator(CreditDetailsActivity.this));
                statusBarColorAnim.start();
            }

            if (isDark) {
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        new int[]{
                                ContextCompat.getColor(CreditDetailsActivity.this, android.R.color.transparent),
                                statusBarColor});

                appBarLayout.setBackground(gradientDrawable);
                collapsingToolbarLayout.setContentScrim(new ColorDrawable(PaletteColorUtils.modifyAlpha(statusBarColor, 0.9f)));
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        new int[]{
                                ContextCompat.getColor(CreditDetailsActivity.this, android.R.color.transparent),
                                ContextCompat.getColor(CreditDetailsActivity.this, R.color.grey)});

                appBarLayout.setBackground(gradientDrawable);
                collapsingToolbarLayout.setContentScrim(new ColorDrawable(PaletteColorUtils.modifyAlpha(ContextCompat.getColor(CreditDetailsActivity.this, R.color.grey), 0.9f)));
            }

        });
    }

    private void creditResponseItems(CreditResponseCombined response) {
        creditViewStub = findViewById(R.id.credits_cast_view);
        View creditDetailsView = creditViewStub.inflate();
        personRecyclerView = creditDetailsView.findViewById(R.id.movies_person_recycler_view);

        crewViewStub = findViewById(R.id.credits_crew_view);
        View creditCrewDetailsView = crewViewStub.inflate();
        crewRecyclerView = creditCrewDetailsView.findViewById(R.id.movies_person_crew_recycler_view);

        personRecyclerView.setItemAnimator(new DefaultItemAnimator());
        crewRecyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        personRecyclerView.setLayoutManager(layoutManager);
        crewRecyclerView.setLayoutManager(layoutManager2);

        castCredits = response.getCast();
        crewCredits = response.getCrew();

        personDetailsAdapter = new PersonDetailsAdapter(combineCreditsForUI(), this);

        personRecyclerView.setAdapter(personDetailsAdapter);
        crewRecyclerView.setAdapter(personDetailsAdapter);
    }

    private ArrayList<CombinedPersonResponse> combineCreditsForUI() {
        ArrayList<CombinedPersonResponse> combinedCreditsResponseList = new ArrayList<>();
        for (CombinedCastCredit castCredit : castCredits) {
            CombinedPersonResponse combinedCreditsResponse = new CombinedPersonResponse();
            combinedCreditsResponse.setOriginalTitle(castCredit.getOriginalTitle());
            combinedCreditsResponse.setCharacter(castCredit.getCharacter());
            combinedCreditsResponse.setReleaseDate(castCredit.getReleaseDate());
            combinedCreditsResponse.setPosterPath(castCredit.getPosterPath());
            combinedCreditsResponseList.add(combinedCreditsResponse);
        }

        for (CombinedCrewCredits crewCredit : crewCredits) {
            CombinedPersonResponse combinedCreditsResponse = new CombinedPersonResponse();
            combinedCreditsResponse.setOriginalTitle(crewCredit.getOriginalTitle());
            combinedCreditsResponse.setJob(crewCredit.getJob());
            combinedCreditsResponse.setReleaseDate(crewCredit.getReleaseDate());
            combinedCreditsResponse.setBackdropPath(crewCredit.getBackdropPath());
            combinedCreditsResponseList.add(combinedCreditsResponse);
        }

        return combinedCreditsResponseList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
