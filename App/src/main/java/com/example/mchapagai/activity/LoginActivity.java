package com.example.mchapagai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.library.utils.MaterialDialogUtils;
import com.example.mchapagai.R;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.model.AccountDetails;
import com.example.mchapagai.model.AuthSession;
import com.example.mchapagai.model.AuthToken;
import com.example.mchapagai.utils.SharedPreferencesUtils;
import com.example.mchapagai.view_model.MovieViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
                        new Consumer<AuthToken>() {
                            @Override
                            public void accept(AuthToken token) {
                                authenticationToken = token.getRequestToken();
                                preferencesUtils.setAccessToken(authenticationToken);
                                Log.d("Authentication token ", authenticationToken);
                                requestTokenAccess = true;
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                preferencesUtils.setAccessTokenFalse();
                                requestTokenAccess = false;
                                MaterialDialogUtils.showDialog(LoginActivity.this,
                                        R.string.service_error_title,
                                        R.string.service_error_404,
                                        R.string.material_dialog_ok);
                            }
                        }
                ));
    }


    private void initViews() {
        loginButton = findViewById(R.id.login_button);
        usernameInputFiled = findViewById(R.id.username_edit_text);
        passwordInputField = findViewById(R.id.password_edit_text);
        preferencesUtils = new SharedPreferencesUtils(this);
    }

    private void setupUserLogin() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInputFiled.getText().toString();
                final String password = passwordInputField.getText().toString();

                compositeDisposable.add(movieViewModel.getRequestAuthenticated(authenticationToken, username, password)
                        .subscribe(
                                new Consumer<AuthToken>() {
                                    @Override
                                    public void accept(AuthToken token) {
                                        verifyToken = true;
                                        preferencesUtils.setAccessTokenVerified();
                                        authenticationToken = token.getRequestToken();
                                        Log.d(TAG, token.getRequestToken());

                                        compositeDisposable.add(movieViewModel.getSessionID(authenticationToken)
                                                .subscribe(
                                                        new Consumer<AuthSession>() {
                                                            @Override
                                                            public void accept(AuthSession authSession) {
                                                                sessionId = authSession.getSessionId();
                                                                preferencesUtils.setUserSessionId(sessionId);

                                                                getAccountSignInDetails();

                                                                Intent intent = new Intent();
                                                                intent.setClass(LoginActivity.this, LandingActivity.class);
                                                                if (!stopped) {
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        }
                                                ));
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        preferencesUtils.setAccessTokenVerifiedFalse();
                                        verifyToken = false;
                                        MaterialDialogUtils.showDialog(LoginActivity.this,
                                                R.string.service_error_title,
                                                R.string.service_error_404,
                                                R.string.material_dialog_ok);
                                    }
                                }
                        ));
            }
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
                new Consumer<AccountDetails>() {
                    @Override
                    public void accept(AccountDetails accountDetails) {
                        if (accountDetails.getId() != null && accountDetails.getUsername() != null) {
                            preferencesUtils.setAccountDetails(accountDetails.getId(), accountDetails.getUsername());
                        } else {
                            preferencesUtils.setAccountIDFalse();
                        }
                    }
                }
        ));
    }
}

