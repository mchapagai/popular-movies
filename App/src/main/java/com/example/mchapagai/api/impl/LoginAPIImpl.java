package com.example.mchapagai.api.impl;

import com.example.mchapagai.api.LoginAPI;
import com.example.mchapagai.model.AccountDetails;
import com.example.mchapagai.model.AuthSession;
import com.example.mchapagai.model.AuthToken;
import com.example.mchapagai.service.LoginService;

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
