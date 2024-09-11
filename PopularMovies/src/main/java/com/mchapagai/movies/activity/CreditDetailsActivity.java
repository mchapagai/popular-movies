package com.mchapagai.movies.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Palette.Swatch;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mchapagai.core.response.people.CastCreditResponse;
import com.mchapagai.core.response.people.CombinedPersonResponse;
import com.mchapagai.core.response.people.CrewCreditResponse;
import com.mchapagai.core.response.people.PersonResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.CastAdapter;
import com.mchapagai.movies.adapter.CrewAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.utils.AnimationUtils;
import com.mchapagai.movies.utils.MaterialDialogUtils;
import com.mchapagai.movies.utils.PaletteColorUtils;
import com.mchapagai.movies.utils.PaletteColorUtils.ColorUtils;
import com.mchapagai.movies.view_model.PeopleViewModel;
import com.mchapagai.movies.views.MaterialCircleImageView;
import com.mchapagai.movies.views.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class CreditDetailsActivity extends BaseActivity {

    private static final String TAG = CreditDetailsActivity.class.getSimpleName();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PersonResponse personResponse;
    private int statusBarColor;
    private int personId;

    Toolbar toolbar;
    AppBarLayout appbarLayout;
    MaterialTextView birthDate;
    MaterialCircleImageView profileImageView;
    CollapsingToolbarLayout creditCollapsingToolbarLayout;
    LinearLayout creditDetailsChildLayout;
    MaterialTextView creditsBiography;
    TextView creditsPersonName;
    RecyclerView moviesCastRecyclerView;
    RecyclerView moviesCrewRecyclerView;
    MaterialTextView castCreditTitle;
    MaterialTextView crewCreditTitle;

    @Inject
    PeopleViewModel peopleViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.person_details_container_layout);

        toolbar = findViewById(R.id.credit_toolbar);
        appbarLayout = findViewById(R.id.app_bar_layout);
        birthDate = findViewById(R.id.birth_date);
        profileImageView = findViewById(R.id.credit_image_view);
        creditCollapsingToolbarLayout = findViewById(R.id.credit_collapsing_toolbar_layout);
        creditDetailsChildLayout = findViewById(R.id.credit_details_child_layout);
        creditsBiography = findViewById(R.id.credits_biography);
        creditsPersonName = findViewById(R.id.credits_person_name);
        moviesCastRecyclerView = findViewById(R.id.movies_cast_recycler_view);
        moviesCrewRecyclerView = findViewById(R.id.movies_crew_recycler_view);
        castCreditTitle = findViewById(R.id.cast_credit_title);
        crewCreditTitle = findViewById(R.id.crew_credit_title);

        // Fullscreen - hide the status bar
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("");
        // Implement addOnOffsetChangedListener to show CollapsingToolbarLayout Tile only when
        // collapsed
        appbarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            boolean isShown = true;

            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (personResponse != null) {
                        creditCollapsingToolbarLayout.setTitle(personResponse.getName());
                    }
                    isShown = true;
                } else if (isShown) {
                    // There should be a space between double quote otherwise it won't work
                    creditCollapsingToolbarLayout.setTitle(" ");
                    isShown = false;
                }
            }
        });

        Intent i = getIntent();
        personId = i.getIntExtra(Constants.PERSON_ID_INTENT, 0);

        loadPersonDetails();
        loadCreditDetails();
        loadCrewDetails();
        showDetailsAnimation();
    }

    private void loadPersonDetails() {

        compositeDisposable.add(peopleViewModel.getPersonDetailsById(personId)
                .subscribe(
                        response -> {
                            if (response != null) {
                                personResponse = response;

                                displayImage(personResponse.getProfilePath());
                                creditsPersonName.setText(response.personName());
                                creditsBiography.setText(response.getBiography());
                                if (response.getBirthday() != null) {
                                    birthDate.setText(response.birthDayAndPlace());
                                } else {
                                    birthDate.setVisibility(View.GONE);
                                }
                            }
                        }, throwable -> MaterialDialogUtils.showDialog(this,
                                R.string.service_error_title, R.string.service_error_title,
                                R.string.material_dialog_ok)
                ));
    }

    private void loadCreditDetails() {
        compositeDisposable.add(peopleViewModel.getPersonCombinedDetailsById(personId)
                .subscribe(
                        response -> castResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(this,
                                getString(R.string.service_error_title), throwable.getMessage(),
                                getString(R.string.material_dialog_ok))
                ));
    }

    private void loadCrewDetails() {
        Observable<CrewCreditResponse> distinct = peopleViewModel
                .getPersonCombinedDetailsById(personId)
                .flatMap(response -> Observable.just(response.getCrew())
                        .flatMapIterable(
                                combinedCrewCredits -> combinedCrewCredits))
                .distinct(crewItems -> crewItems.getTitle());

        compositeDisposable
                .add(distinct.toList()
                        .toObservable()
                        .subscribe(
                                combinedCrewCreditsList -> crewResponseItems(
                                        combinedCrewCreditsList),
                                throwable -> MaterialDialogUtils.showDialog(this,
                                        getString(R.string.service_error_title),
                                        throwable.getMessage(),
                                        getString(R.string.material_dialog_ok))
                        ));
    }

    private void castResponseItems(CombinedPersonResponse response) {
        List<CastCreditResponse> castCredit = response.getCast();
        if (!castCredit.isEmpty()) {
            moviesCastRecyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager castLayout = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            moviesCastRecyclerView.setLayoutManager(castLayout);
            moviesCastRecyclerView.setAdapter(new CastAdapter(castCredit, this));
        } else {
            castCreditTitle.setVisibility(View.GONE);
        }
    }

    private void crewResponseItems(List<CrewCreditResponse> crewCredits) {
        if (!crewCredits.isEmpty()) {
            moviesCrewRecyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager crewLayout = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            moviesCrewRecyclerView.setLayoutManager(crewLayout);
            moviesCrewRecyclerView.setAdapter(new CrewAdapter(crewCredits, this));
        } else {
            crewCreditTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDetailsAnimation() {
        final int targetHeight = AnimationUtils.getTargetHeight(creditDetailsChildLayout);
        Animation animation = AnimationUtils.getExpandHeightAnimation(creditDetailsChildLayout,
                targetHeight);
        animation.setDuration(
                (int) (targetHeight / creditDetailsChildLayout.getContext().getResources()
                        .getDisplayMetrics().density));
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setStartOffset(Constants.START_OFFSET);
        creditDetailsChildLayout.startAnimation(animation);
    }

    private void displayImage(String profileImagePath) {

        Picasso.get().load(Constants.SECURE_IMAGE_ENDPOINT + profileImagePath).into(
                profileImageView);

        final Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            boolean isDark;
            @ColorUtils int lightness = PaletteColorUtils.isDark(palette);
            if (lightness == Constants.UNKNOWN) {
                isDark = PaletteColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
            } else {
                isDark = lightness == Constants.DARK_COLOR;
            }

            if (!isDark) {
                // Make back icon dark on light images
                ImageButton backButton = (ImageButton) toolbar.getChildAt(0);
                backButton
                        .setColorFilter(ContextCompat.getColor(CreditDetailsActivity.this,
                                R.color.darkThemePrimary));

                // Make toolbar title text color dark
                creditCollapsingToolbarLayout.setCollapsedTitleTextColor(
                        ContextCompat.getColor(CreditDetailsActivity.this,
                                R.color.darkThemeSecondary));
            }

            // color the status bar.
            statusBarColor = getWindow().getStatusBarColor();
            final Swatch topColor = PaletteColorUtils.getMostPopulousSwatch(palette);
            if (topColor != null && isDark) {
                statusBarColor = PaletteColorUtils.scrimify(topColor.getRgb(), true,
                        Constants.SCRIM_ADJUSTMENT);
                // set a light status bar
            }

            if (statusBarColor != getWindow().getStatusBarColor()) {
                ValueAnimator statusBarColorAnim = ValueAnimator
                        .ofArgb(getWindow().getStatusBarColor(), statusBarColor);
                statusBarColorAnim.addUpdateListener(
                        animation -> getWindow().setStatusBarColor(
                                (int) animation.getAnimatedValue()));
                statusBarColorAnim.setDuration(500L);
                statusBarColorAnim
                        .setInterpolator(AnimationUtils.getFastOutSlowInInterpolator(
                                CreditDetailsActivity.this));
                statusBarColorAnim.start();
            }

            if (isDark) {
                GradientDrawable gradientDrawable = new GradientDrawable(
                        Orientation.BOTTOM_TOP,
                        new int[]{
                                ContextCompat.getColor(CreditDetailsActivity.this,
                                        android.R.color.transparent),
                                statusBarColor});

                appbarLayout.setBackground(gradientDrawable);
                creditCollapsingToolbarLayout
                        .setContentScrim(new ColorDrawable(
                                PaletteColorUtils.modifyAlpha(statusBarColor, 0.9f)));
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable(
                        Orientation.BOTTOM_TOP,
                        new int[]{
                                ContextCompat.getColor(CreditDetailsActivity.this,
                                        android.R.color.transparent),
                                ContextCompat.getColor(CreditDetailsActivity.this, R.color.grey)});

                appbarLayout.setBackground(gradientDrawable);
                creditCollapsingToolbarLayout.setContentScrim(new ColorDrawable(PaletteColorUtils
                        .modifyAlpha(
                                ContextCompat.getColor(CreditDetailsActivity.this, R.color.grey),
                                0.9f)));
            }

        });
    }
}
