package com.mchapagai.movies.api;


import com.mchapagai.movies.model.account.AccountDetails;
import com.mchapagai.movies.model.account.AuthSession;
import com.mchapagai.movies.model.account.AuthToken;

import io.reactivex.Single;

public interface LoginAPI {

    Single<AuthToken> getAuthRequestToken();
    Single<AuthToken> getRequestAuthenticated(String requestToken, String username, String password);
    Single<AuthSession> getSessionId(String requestToken);
    Single<AccountDetails> getAccountDetails(String sessionId);

}
