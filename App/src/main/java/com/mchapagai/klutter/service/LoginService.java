package com.mchapagai.klutter.service;

import com.mchapagai.klutter.model.account.AccountDetails;
import com.mchapagai.klutter.model.account.AuthSession;
import com.mchapagai.klutter.model.account.AuthToken;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {

    @GET("authentication/token/new")
    Single<AuthToken> getRequestToken();

    @GET("authentication/token/validate_with_login")
    Single<AuthToken> getRequestAuthenticated(@Query("request_token") String requestToken,
            @Query("username") String username, @Query("password") String password);

    @GET("authentication/session/new")
    Single<AuthSession> getSessionID(@Query("request_token") String requestToken);

    @GET("account")
    Single<AccountDetails> getAccountDetails(@Query("session_id") String session_id);

}
