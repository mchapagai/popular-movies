package com.example.mchapagai.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.library.utils.MaterialDialogUtils;
import com.example.library.utils.PaletteColorUtils;
import com.example.library.views.MaterialCircleImageView;
import com.example.library.views.MaterialImageView;
import com.example.library.views.MaterialTextView;
import com.example.mchapagai.R;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.binding.PersonResponse;
import com.example.mchapagai.view_model.MovieViewModel;
import com.squareup.picasso.Picasso;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;

public class CreditDetailsActivity extends BaseActivity {

    private static final String TAG = CreditDetailsActivity.class.getSimpleName();

    @Inject
    MovieViewModel movieViewModel;

    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private PersonResponse personResponse;
    private MaterialCircleImageView circleImageView;
    private MaterialImageView heroTemplate;
    private TextView nameView, biographyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.person_details_container_layout);

        // Fullscreen - hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = findViewById(R.id.credit_toolbar);
        collapsingToolbarLayout = findViewById(R.id.credit_collapsing_toolbar_layout);
        appBarLayout = findViewById(R.id.credit_app_bar_layout);
        circleImageView = findViewById(R.id.credit_circle_image_view);
        coordinatorLayout = findViewById(R.id.details_layout);
        heroTemplate = findViewById(R.id.credit_hero_template);
        nameView = findViewById(R.id.credits_person_name);
        biographyView = findViewById(R.id.credits_biography);

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
        int id = i.getIntExtra(Constants.PERSON_ID_INTENT, 0);
        Log.i(TAG, "UserId: " + id);

//        recyclerView = findViewById(R.id.movies_person_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        compositeDisposable.add(movieViewModel.getPersonDetailsById(String.valueOf(id))
                .subscribe(
                        response -> {
                            if (response != null) {
                                personResponse = response;

                                displayImage(personResponse.getProfilePath());
                                nameView.setText(response.getName());
                                biographyView.setText(response.getBiography());
                            }
//                            recyclerView.setAdapter(new PersonDetailsAdapter(Collections.singletonList(response)));

//                            materialTextView.setText(response.getBiography());
//                            materialTextView.setText(response.getName());
                        }, throwable -> MaterialDialogUtils.showDialog(CreditDetailsActivity.this,
                                R.string.service_error_title, R.string.service_error_title, R.string.material_dialog_ok)
                ));
    }

    private void displayImage(String profileImagePath) {
        Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + profileImagePath)
                .into(new com.squareup.picasso.Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        circleImageView.setImageBitmap(bitmap);

                        Palette.from(bitmap).generate(palette -> {
                            appBarLayout.setBackground(PaletteColorUtils.getGradientDrawable(
                                    PaletteColorUtils.topColor(palette),
                                    PaletteColorUtils.centerLightColor(palette),
                                    PaletteColorUtils.bottomDarkColor(palette)));

                            Palette.Swatch vibrant = palette.getVibrantSwatch();
                            if (vibrant != null) {
                                nameView.setTextColor(vibrant.getTitleTextColor());
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
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
