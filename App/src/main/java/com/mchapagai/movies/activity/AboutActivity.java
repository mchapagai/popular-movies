package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.BuildConfig;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.circle_image_view)           MaterialCircleImageView circleImageView;
    @BindView(R.id.about_app_version)           MaterialTextView aboutAppVersion;
    @BindView(R.id.about_toolbar)               Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)   CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)              AppBarLayout appBarLayout;
    @BindView(R.id.licenses_layout)             LinearLayout licensesLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity_container);
        ButterKnife.bind(this);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        initLicenses();
        initViews();
    }

    private void initViews() {

        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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
                    collapsingToolbar.setTitle(getString(R.string.title_about_activity));
                    isShown = true;
                } else if (isShown) {
                    // There should be a space between double quote otherwise it won't work
                    collapsingToolbar.setTitle(" ");
                    isShown = false;
                }
            }
        });
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        aboutAppVersion.setVisibility(View.VISIBLE);
        aboutAppVersion.setText(getString(R.string.msg_app_version, BuildConfig.VERSION_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initLicenses() {
        LayoutInflater inflater = LayoutInflater.from(this);

        String[] softwareList = getResources().getStringArray(R.array.software_list);
        String[] licenseList = getResources().getStringArray(R.array.license_list);
        licensesLayout.addView(createItemsText(softwareList));
        for (int i = 0; i < softwareList.length; i++) {
            licensesLayout.addView(createDivider(inflater, licensesLayout));
            licensesLayout.addView(createHeader(softwareList[i]));
            licensesLayout.addView(createHtmlText(licenseList[i]));
        }
    }

    private TextView createHeader(final String name) {
        String s = "<big><b>" + name + "</b></big>";
        return createHtmlText(s, 8);
    }

    private TextView createItemsText(final String... names) {
        StringBuilder s = new StringBuilder();
        for (String name : names) {
            if (s.length() > 0) {
                s.append("<br>");
            }
            s.append("\u2022 ");
            s.append(name);
        }
        return createHtmlText(s.toString(), 8);
    }

    private TextView createHtmlText(final String s) {
        return createHtmlText(s, 8);
    }

    private TextView createHtmlText(final String s, final int margin) {
        TextView text = new TextView(this);
        text.setAutoLinkMask(Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        text.setText(Html.fromHtml(s));
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginPx = (0 < margin) ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin,
                getResources().getDisplayMetrics()) : 0;
        layoutParams.setMargins(0, marginPx, 0, marginPx);
        text.setLayoutParams(layoutParams);
        return text;
    }

    private View createDivider(final LayoutInflater inflater, final ViewGroup parent) {
        return inflater.inflate(R.layout.divider, parent, false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}