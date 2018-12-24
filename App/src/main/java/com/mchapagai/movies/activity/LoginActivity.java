package com.mchapagai.movies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.MaterialButton;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.model.AuthSession;
import com.mchapagai.movies.model.AuthToken;
import com.mchapagai.movies.model.binding.CombinedAuthResponse;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.view_model.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.toolbar)                     Toolbar toolbar;
    @BindView(R.id.login_button)                MaterialButton loginButton;
    @BindView(R.id.username_edit_text)          EditText usernameInputFiled;
    @BindView(R.id.password_edit_text)          EditText passwordInputField;
    @BindView(R.id.progress_page_loader)        PageLoader pageLoader;
    @BindView(R.id.navigate_to_about)           MaterialTextView aboutView;
    @BindView(R.id.navigate_to_tmdb)            MaterialTextView tmdbSite;

    @Inject
    LoginViewModel loginViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String authenticationToken;
    boolean requestTokenAccess, verifyToken, stopped;
    private PreferencesHelper preferencesUtils;
    private String sessionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout_container);
        ButterKnife.bind(this);
        stopped = false;
        preferencesUtils = new PreferencesHelper(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        fetchAuthenticationToken();
    }

    private void fetchAuthenticationToken() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(loginViewModel.getAuthRequestToken()
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(token -> {
                            if (token.getRequestToken() != null) {
                                authenticationToken = token.getRequestToken();
                                preferencesUtils.setAccessToken(authenticationToken);
                                Log.d("Authentication token ", authenticationToken);
                                requestTokenAccess = true;
                            } else {
                                errorDialog();
                            }
                        }, throwable -> {
                            requestTokenAccess = false;
                            errorDialog();
                        }
                ));
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

    private View.OnClickListener navigateToAbout = view -> {
        startActivity(new Intent(view.getContext(), AboutActivity.class));
    };

    private View.OnClickListener getNavigateToTMDb = v -> {
        String url = "https://www.themoviedb.org/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    };

    private void setupUserLogin() {
        loginButton.setOnClickListener(v -> {
            final String username = usernameInputFiled.getText().toString();
            final String password = passwordInputField.getText().toString();

            Single<AuthToken> authenticatedSingle = loginViewModel.getRequestAuthenticated(authenticationToken, username, password);
            Single<AuthSession> sessionIdSingle = loginViewModel.getSessionID(authenticationToken);

            compositeDisposable.add(Single.zip(authenticatedSingle, sessionIdSingle, CombinedAuthResponse::new)
                    .subscribe(combinedAuthResponse -> {
                                verifyToken = true;
                                preferencesUtils.setAccessTokenVerified();
                                authenticationToken = combinedAuthResponse.getAuthToken().getRequestToken();
                                Log.d(TAG, combinedAuthResponse.getAuthToken().getRequestToken());

                                sessionId = combinedAuthResponse.getAuthSession().getSessionId();
                                preferencesUtils.setUserSessionId(sessionId);
                                getAccountSignInDetails();
                                if (!stopped) {
                                    startActivity(new Intent(LoginActivity.this, DiscoverMoviesActivity.class));
                                }
                            }, throwable -> {
                                preferencesUtils.setAccessTokenVerifiedFalse();
                                verifyToken = false;
                                errorDialog();
                            }
                    ));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopped = false;
        setupUserLogin();
    }

    @Override
    protected void onPause() {
        stopped = true;
        requestTokenAccess = false;
        verifyToken = false;

        if (authenticationToken != null) {
            authenticationToken = null;
        }

        super.onPause();
    }

    private void getAccountSignInDetails() {
        String sessionId = preferencesUtils.setSessionKey();
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(loginViewModel.getAccountDetails(sessionId)
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(accountDetails -> {
                            if (accountDetails.getUsername() != null) {
                                preferencesUtils.setAccountDetails(String.valueOf(accountDetails.getId()), accountDetails.getUsername());
                            } else {
                                preferencesUtils.setAccountIDFalse();
                            }
                        }
                ));
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

