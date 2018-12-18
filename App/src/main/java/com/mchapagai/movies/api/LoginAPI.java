package com.mchapagai.movies.api;


import com.mchapagai.movies.model.AccountDetails;
import com.mchapagai.movies.model.AuthSession;
import com.mchapagai.movies.model.AuthToken;

import io.reactivex.Single;

public interface LoginAPI {

    Single<AuthToken> getAuthRequestToken();
    Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password);
    Single<AuthSession> getSessionID(String requestToken);
    Single<AccountDetails> getAccountDetails(String sessionId);

}
