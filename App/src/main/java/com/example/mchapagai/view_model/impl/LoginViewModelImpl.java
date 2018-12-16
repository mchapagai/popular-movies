package com.example.mchapagai.view_model.impl;

import com.example.mchapagai.api.LoginAPI;
import com.example.mchapagai.model.AccountDetails;
import com.example.mchapagai.model.AuthSession;
import com.example.mchapagai.model.AuthToken;
import com.example.mchapagai.utils.RxUtils;
import com.example.mchapagai.view_model.LoginViewModel;
import io.reactivex.Single;

import javax.inject.Inject;

public class LoginViewModelImpl implements LoginViewModel {

    private LoginAPI loginAPI;

    @Inject
    public LoginViewModelImpl(LoginAPI loginAPI) {
        this.loginAPI = loginAPI;
    }

    @Override
    public Single<AuthToken> getAuthRequestToken() {
        return loginAPI.getAuthRequestToken().compose(RxUtils.applySingleSchedulers());
    }

    @Override
    public Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password) {
        return loginAPI.getRequestAuthenticated(requestToken, username, password).compose(RxUtils.applySingleSchedulers());
    }

    @Override
    public Single<AuthSession> getSessionID(String requestToken) {
        return loginAPI.getSessionID(requestToken).compose(RxUtils.applySingleSchedulers());
    }

    @Override
    public Single<AccountDetails> getAccountDetails(String sessionId) {
        return loginAPI.getAccountDetails(sessionId).compose(RxUtils.applySingleSchedulers());
    }
}
