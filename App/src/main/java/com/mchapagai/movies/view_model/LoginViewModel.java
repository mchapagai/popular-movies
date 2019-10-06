package com.mchapagai.movies.view_model;

import com.mchapagai.movies.model.account.AccountDetails;

import io.reactivex.Single;

public interface LoginViewModel {

    Single<AccountDetails> getRequestAuthenticated(String username, String password);

    Single<AccountDetails> getAccountDetails(String sessionId);

}
