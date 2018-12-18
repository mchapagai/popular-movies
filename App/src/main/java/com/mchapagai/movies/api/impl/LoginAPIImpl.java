package com.mchapagai.movies.api.impl;

import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.model.AccountDetails;
import com.mchapagai.movies.model.AuthSession;
import com.mchapagai.movies.model.AuthToken;
import com.mchapagai.movies.service.LoginService;

import javax.inject.Provider;

import io.reactivex.Single;

public class LoginAPIImpl implements LoginAPI {

    private Provider<LoginService> loginService;

    public LoginAPIImpl(Provider<LoginService> service) {
        this.loginService = service;
    }

    @Override
    public Single<AuthToken> getAuthRequestToken() {
        return loginService.get().getRequestToken();
    }

    @Override
    public Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password) {
        return loginService.get().getRequestAuthenticated(requestToken, username, password);
    }

    @Override
    public Single<AuthSession> getSessionID(String requestToken) {
        return loginService.get().getSessionID(requestToken);
    }

    @Override
    public Single<AccountDetails> getAccountDetails(String sessionId) {
        return loginService.get().getAccountDetails(sessionId);
    }
}
