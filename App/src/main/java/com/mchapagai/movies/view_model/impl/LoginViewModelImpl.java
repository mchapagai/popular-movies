package com.mchapagai.movies.view_model.impl;


import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.model.account.AccountDetails;
import com.mchapagai.movies.model.account.AuthSession;
import com.mchapagai.movies.model.account.AuthToken;
import com.mchapagai.movies.utils.RxUtils;
import com.mchapagai.movies.view_model.LoginViewModel;

import javax.inject.Inject;

import io.reactivex.Single;

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
