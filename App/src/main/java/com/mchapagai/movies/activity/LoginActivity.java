package com.mchapagai.movies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.MaterialButton;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.view_model.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_button)
    MaterialButton loginButton;
    @BindView(R.id.username_edit_text)
    EditText usernameInputFiled;
    @BindView(R.id.password_edit_text)
    EditText passwordInputField;
    @BindView(R.id.progress_page_loader)
    PageLoader pageLoader;
    @BindView(R.id.navigate_to_about)
    MaterialTextView aboutView;
    @BindView(R.id.navigate_to_tmdb)
    MaterialTextView tmdbSite;

    @Inject
    LoginViewModel loginViewModel;

    @Inject
    PreferencesHelper preferencesUtils;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout_container);
        ButterKnife.bind(this);
        preferencesUtils = new PreferencesHelper(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        setupUserLogin();
    }

    private void setupUserLogin() {
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
        aboutView.setOnClickListener(navigateToAbout);
        tmdbSite.setOnClickListener(getNavigateToTMDb);
    }

    private View.OnClickListener navigateToAbout = view -> startActivity(
            new Intent(view.getContext(), AboutActivity.class));

    private View.OnClickListener getNavigateToTMDb = v -> {
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
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

