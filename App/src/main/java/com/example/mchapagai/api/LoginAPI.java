package com.example.mchapagai.api;

import com.example.mchapagai.model.AccountDetails;
import com.example.mchapagai.model.AuthSession;
import com.example.mchapagai.model.AuthToken;

import io.reactivex.Single;

public interface LoginAPI {

    Single<AuthToken> getAuthRequestToken();
    Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password);
    Single<AuthSession> getSessionID(String requestToken);
    Single<AccountDetails> getAccountDetails(String sessionId);

}
