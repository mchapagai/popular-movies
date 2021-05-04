package com.mchapagai.klutter.api;

import com.mchapagai.klutter.model.account.AccountDetails;
import com.mchapagai.klutter.model.account.AuthSession;
import com.mchapagai.klutter.model.account.AuthToken;

import io.reactivex.Single;

public interface LoginAPI {

    Single<AuthToken> getAuthRequestToken();

    Single<AuthToken> getRequestAuthenticated(String requestToken, String username,
            String password);

    Single<AuthSession> getSessionId(String requestToken);

    Single<AccountDetails> getAccountDetails(String sessionId);

}
