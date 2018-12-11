package com.example.mchapagai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.library.utils.MaterialDialogUtils;
import com.example.mchapagai.R;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.utils.SharedPreferencesUtils;
import com.example.mchapagai.view_model.MovieViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    MovieViewModel movieViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String authenticationToken;
    boolean requestTokenAccess, verifyToken, stopped;
    private SharedPreferencesUtils preferencesUtils;
    private EditText usernameInputFiled, passwordInputField;
    private Button loginButton;
    private String sessionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout_container);

        stopped = false;

        initViews();
        fetchAuthenticationToken();
    }

    private void fetchAuthenticationToken() {
        compositeDisposable.add(movieViewModel.getAuthRequestToken()
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
        MaterialDialogUtils.showDialog(LoginActivity.this,
                R.string.service_error_title,
                R.string.service_error_404,
                R.string.material_dialog_ok);
    }

    private void initViews() {
        loginButton = findViewById(R.id.login_button);
        usernameInputFiled = findViewById(R.id.username_edit_text);
        passwordInputField = findViewById(R.id.password_edit_text);
        preferencesUtils = new SharedPreferencesUtils(this);
    }

    private void setupUserLogin() {
        loginButton.setOnClickListener(v -> {
            final String username = usernameInputFiled.getText().toString();
            final String password = passwordInputField.getText().toString();

            compositeDisposable.add(movieViewModel.getRequestAuthenticated(authenticationToken, username, password)
                    .subscribe(
                            token -> {
                                verifyToken = true;
                                preferencesUtils.setAccessTokenVerified();
                                authenticationToken = token.getRequestToken();
                                Log.d(TAG, token.getRequestToken());

                                compositeDisposable.add(movieViewModel.getSessionID(authenticationToken)
                                        .subscribe(
                                                authSession -> {
                                                    sessionId = authSession.getSessionId();
                                                    preferencesUtils.setUserSessionId(sessionId);
                                                    getAccountSignInDetails();
                                                    Intent intent = new Intent();
                                                    intent.setClass(LoginActivity.this, LandingActivity.class);
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
        compositeDisposable.add(movieViewModel.getAccountDetails(sessionId).subscribe(
                accountDetails -> {
                    if (accountDetails.getUsername() != null) {
                        preferencesUtils.setAccountDetails(String.valueOf(accountDetails.getId()), accountDetails.getUsername());
                    } else {
                        preferencesUtils.setAccountIDFalse();
                    }
                }
        ));
    }
}

