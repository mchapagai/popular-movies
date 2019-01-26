package com.mchapagai.movies.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mchapagai.library.common.LibraryConstants;
import com.mchapagai.library.utils.AnimationUtils;
import com.mchapagai.library.utils.DateTimeUtils;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.utils.PaletteColorUtils;
import com.mchapagai.library.utils.PaletteColorUtils.ColorUtils;
import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.CastAdapter;
import com.mchapagai.movies.adapter.CrewAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.CombinedCastCredit;
import com.mchapagai.movies.model.CombinedCrewCredits;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.model.binding.PersonResponse;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

import javax.inject.Inject;

public class CreditDetailsActivity extends BaseActivity {

    private static final String TAG = CreditDetailsActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PersonResponse personResponse;
    private int statusBarColor;
    private int personId;

    @BindView(R.id.credit_toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appbarLayout;

    @BindView(R.id.birth_date)
    MaterialTextView birthDate;

    @BindView(R.id.credit_image_view)
    MaterialCircleImageView profileImageView;

    @BindView(R.id.credit_collapsing_toolbar_layout)
    CollapsingToolbarLayout creditCollapsingToolbarLayout;

    @BindView(R.id.credit_details_child_layout)
    LinearLayout creditDetailsChildLayout;

    @BindView(R.id.credits_biography)
    MaterialTextView creditsBiography;

    @BindView(R.id.credits_person_name)
    TextView creditsPersonName;

    @BindView(R.id.movies_cast_recycler_view)
    RecyclerView moviesCastRecyclerView;

    @BindView(R.id.movies_crew_recycler_view)
    RecyclerView moviesCrewRecyclerView;

    @BindView(R.id.cast_credit_title)
    MaterialTextView castCreditTitle;

    @BindView(R.id.crew_credit_title)
    MaterialTextView crewCreditTitle;

    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.person_details_container_layout);
        ButterKnife.bind(this);

        // Fullscreen - hide the status bar
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Log.i(TAG, "PersonId: " + personId);

        loadPersonDetails();
        loadCreditDetails();
        loadCrewDetails();
        showDetailsAnimation();
    }

    private void loadPersonDetails() {

        compositeDisposable.add(movieViewModel.getPersonDetailsById(personId)
                .subscribe(
                        response -> {
                            if (response != null) {
                                personResponse = response;

                                displayImage(personResponse.getProfilePath());
                                creditsPersonName.setText(
                                        String.format("%s \"%s\"", response.getName(),
                                                response.getKnownForDepartment()));
                                creditsBiography.setText(response.getBiography());
                                if (response.getBirthday() != null) {
                                    String birthDayAndPlace = getString(
                                            R.string.concat_strings_placeholder,
                                            DateTimeUtils.getNameOfMonth(response.getBirthday()),
                                            response.getPlaceOfBirth());
                                    birthDate.setText(birthDayAndPlace);
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
        compositeDisposable.add(movieViewModel.getPersonCombinedDetailsById(personId)
                .subscribe(
                        response -> castResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(this,
                                getString(R.string.service_error_title), throwable.getMessage(),
                                getString(R.string.material_dialog_ok))
                ));
    }

    private void loadCrewDetails() {
        Observable<CombinedCrewCredits> distinct = movieViewModel
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
        List<CombinedCastCredit> castCredit = response.getCast();
        if (!castCredit.isEmpty() && castCredit.size() > 0) {
            moviesCastRecyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager castLayout = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            moviesCastRecyclerView.setLayoutManager(castLayout);
            moviesCastRecyclerView.setAdapter(new CastAdapter(castCredit, this));
        } else {
            castCreditTitle.setVisibility(View.GONE);
        }
    }

    private void crewResponseItems(List<CombinedCrewCredits> crewCredits) {
        if (!crewCredits.isEmpty() && crewCredits.size() > 0) {
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
        animation.setStartOffset(LibraryConstants.START_OFFSET);
        creditDetailsChildLayout.startAnimation(animation);
    }

    private void displayImage(String profileImagePath) {

        Picasso.get().load(Constants.SECURE_IMAGE_ENDPOINT + profileImagePath).into(
                profileImageView);

        final Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            boolean isDark;
            @ColorUtils int lightness = PaletteColorUtils.isDark(palette);
            if (lightness == LibraryConstants.UNKNOWN) {
                isDark = PaletteColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
            } else {
                isDark = lightness == LibraryConstants.DARK_COLOR;
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
                if (!isDark) {
                    AnimationUtils.setLightStatusBar(getWindow().getDecorView());
                }
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
