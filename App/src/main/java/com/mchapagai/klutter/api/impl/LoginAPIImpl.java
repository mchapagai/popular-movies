package com.mchapagai.klutter.api.impl;

import com.mchapagai.klutter.api.LoginAPI;
import com.mchapagai.klutter.model.account.AccountDetails;
import com.mchapagai.klutter.model.account.AuthSession;
import com.mchapagai.klutter.model.account.AuthToken;
import com.mchapagai.klutter.service.LoginService;

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
    public Single<AuthToken> getRequestAuthenticated(String requestToken, String username,
            String password) {
        return loginService.get().getRequestAuthenticated(requestToken, username, password);
    }

    @Override
    public Single<AuthSession> getSessionId(String requestToken) {
        return loginService.get().getSessionID(requestToken);
    }

    @Override
    public Single<AccountDetails> getAccountDetails(String sessionId) {
        return loginService.get().getAccountDetails(sessionId);
    }
}
