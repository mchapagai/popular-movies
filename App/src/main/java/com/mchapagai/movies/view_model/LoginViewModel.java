package com.mchapagai.movies.view_model;

import com.mchapagai.movies.model.account.AccountDetails;
import com.mchapagai.movies.model.account.AuthSession;
import com.mchapagai.movies.model.account.AuthToken;
import com.mchapagai.movies.model.account.CombinedAuthResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface LoginViewModel {

    Single<AccountDetails> getRequestAuthenticated(String username, String password);
    Single<AccountDetails> getAccountDetails(String sessionId);

}
