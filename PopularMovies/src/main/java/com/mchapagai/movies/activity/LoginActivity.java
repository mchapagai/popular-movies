package com.mchapagai.movies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.utils.MaterialDialogUtils;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.view_model.LoginViewModel;
import com.mchapagai.movies.views.MaterialTextView;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    @Inject
    LoginViewModel loginViewModel;

    @Inject
    PreferencesHelper preferencesUtils;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout_container);
        preferencesUtils = new PreferencesHelper(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initViews();

        setupUserLogin();
    }

    private void setupUserLogin() {
        MaterialButton loginButton = findViewById(R.id.login_button);
        EditText usernameInputFiled = findViewById(R.id.username_edit_text);
        EditText passwordInputField = findViewById(R.id.password_edit_text);

        loginButton.setOnClickListener(v -> {
            final String username = usernameInputFiled.getText().toString();
            final String password = passwordInputField.getText().toString();

            compositeDisposable.add(loginViewModel.getRequestAuthenticated(username,
                    password).subscribe(
                    accountDetails -> {
                        if (accountDetails.getUsername() != null) {
                            preferencesUtils.setAccountDetails(
                                    String.valueOf(accountDetails.getId()),
                                    accountDetails.getUsername());
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        } else {
                            preferencesUtils.setAccountIDFalse();
                            errorDialog();
                        }
                    }, throwable -> Log.e("error message", throwable.getMessage())));
        });
    }

    private void errorDialog() {
        MaterialDialogUtils.showDialog(this,
                R.string.service_error_title,
                R.string.service_error_404,
                R.string.material_dialog_ok);
    }

    private void initViews() {
        MaterialTextView aboutView = findViewById(R.id.navigate_to_about);
        MaterialTextView tmdbSite = findViewById(R.id.navigate_to_tmdb);
        aboutView.setOnClickListener(navigateToAbout);
        tmdbSite.setOnClickListener(getNavigateToTMDb);
    }

    private final View.OnClickListener navigateToAbout = view -> startActivity(
            new Intent(view.getContext(), AboutActivity.class));

    private final View.OnClickListener getNavigateToTMDb = v -> {
        String url = "https://www.themoviedb.org/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    };

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
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

