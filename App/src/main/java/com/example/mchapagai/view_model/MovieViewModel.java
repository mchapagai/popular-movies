package com.example.mchapagai.view_model;

import com.example.mchapagai.model.AccountDetails;
import com.example.mchapagai.model.AuthSession;
import com.example.mchapagai.model.AuthToken;

import io.reactivex.Single;

public interface MovieViewModel {

    Single<AuthToken> getAuthRequestToken();
    Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password);
    Single<AuthSession> getSessionID(String requestToken);
    Single<AccountDetails> getAccountDetails(String sessionId);
}
