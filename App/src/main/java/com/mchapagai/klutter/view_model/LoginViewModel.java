package com.mchapagai.klutter.view_model;

import com.mchapagai.klutter.model.account.AccountDetails;

import io.reactivex.Single;

public interface LoginViewModel {

    Single<AccountDetails> getRequestAuthenticated(String username, String password);

    Single<AccountDetails> getAccountDetails(String sessionId);

}
