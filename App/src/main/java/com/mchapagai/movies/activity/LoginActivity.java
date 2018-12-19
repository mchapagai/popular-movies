package com.mchapagai.movies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.utils.PreferencesHelper;
import com.mchapagai.movies.view_model.LoginViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginViewModel loginViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String authenticationToken;
    boolean requestTokenAccess, verifyToken, stopped;
    private PreferencesHelper preferencesUtils;
    private EditText usernameInputFiled, passwordInputField;
    private Button loginButton;
    private String sessionId;
    private PageLoader pageLoader;
    private MaterialTextView aboutView, tmdbSite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout_container);

        stopped = false;

        initViews();
        fetchAuthenticationToken();
    }

    private void fetchAuthenticationToken() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(loginViewModel.getAuthRequestToken()
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        token -> {
                            if (token.getRequestToken() != null) {
                                authenticationToken = token.getRequestToken();
                                preferencesUtils.setAccessToken(authenticationToken);
                                Log.d("Authentication token ", authenticationToken);
                                requestTokenAccess = true;
                            } else {
                                errorDialog();
                            }
                        }, throwable -> {
                            preferencesUtils.setAccessTokenFalse();
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
        loginButton = findViewById(R.id.login_button);
        usernameInputFiled = findViewById(R.id.username_edit_text);
        passwordInputField = findViewById(R.id.password_edit_text);
        pageLoader = findViewById(R.id.progress_page_loader);
        preferencesUtils = new PreferencesHelper(this);

        aboutView = findViewById(R.id.navigate_to_about);
        aboutView.setOnClickListener(navigateToAbout);
        tmdbSite = findViewById(R.id.navigate_to_tmdb);
        tmdbSite.setOnClickListener(getNavigateToTMDb);
    }

    private View.OnClickListener navigateToAbout = view -> {
        Intent intent = new Intent(view.getContext(), AboutActivity.class);
        startActivity(intent);
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
            pageLoader.setVisibility(View.VISIBLE);
            compositeDisposable.add(loginViewModel.getRequestAuthenticated(authenticationToken, username, password)
                    .doFinally(() -> pageLoader.setVisibility(View.GONE))
                    .subscribe(
                            token -> {
                                verifyToken = true;
                                preferencesUtils.setAccessTokenVerified();
                                authenticationToken = token.getRequestToken();
                                Log.d(TAG, token.getRequestToken());

                                compositeDisposable.add(loginViewModel.getSessionID(authenticationToken)
                                        .subscribe(
                                                authSession -> {
                                                    sessionId = authSession.getSessionId();
                                                    preferencesUtils.setUserSessionId(sessionId);
                                                    getAccountSignInDetails();
                                                    Intent intent = new Intent();
                                                    intent.setClass(LoginActivity.this, DiscoverMoviesActivity.class);
                                                    if (!stopped) {
                                                        startActivity(intent);
                                                    }
                                                }
                                        ));
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
}

