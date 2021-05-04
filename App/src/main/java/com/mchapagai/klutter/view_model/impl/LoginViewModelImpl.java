package com.mchapagai.klutter.view_model.impl;

import com.mchapagai.klutter.api.LoginAPI;
import com.mchapagai.klutter.model.account.AccountDetails;
import com.mchapagai.klutter.model.account.AuthSession;
import com.mchapagai.klutter.model.account.AuthToken;
import com.mchapagai.klutter.model.account.CombinedAuthResponse;
import com.mchapagai.klutter.utils.RxUtils;
import com.mchapagai.klutter.view_model.LoginViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class LoginViewModelImpl implements LoginViewModel {

    private LoginAPI loginAPI;

    @Inject
    public LoginViewModelImpl(LoginAPI loginAPI) {
        this.loginAPI = loginAPI;
    }

    @Override
    public Single<AccountDetails> getAccountDetails(String sessionId) {
        return loginAPI.getAccountDetails(sessionId).compose(RxUtils.applySingleSchedulers());
    }

    @Override
    public Single<AccountDetails> getRequestAuthenticated(String username,
            String password) {
        return loginAPI.getAuthRequestToken()
                .filter(authToken -> authToken.getRequestToken() != null)
                .toSingle()
                .doOnError(throwable -> Observable.just(throwable))
                .flatMap((Function<AuthToken, Single<CombinedAuthResponse>>) authToken -> {
                    Single<AuthToken> authenticatedSingle = loginAPI.getRequestAuthenticated(
                            authToken.getRequestToken(), username, password);

                    Single<AuthSession> sessionIdSingle = loginAPI.getSessionId(
                            authToken.getRequestToken());

                    return Single.zip(authenticatedSingle, sessionIdSingle,
                            (authToken1, authSession) -> {
                                CombinedAuthResponse combinedAuthResponse =
                                        new CombinedAuthResponse();
                                combinedAuthResponse.setSessionId(authSession.getSessionId());
                                combinedAuthResponse.setTokenRequest(
                                        authToken1.getRequestToken());
                                return combinedAuthResponse;
                            });
                }).doOnError(throwable -> Observable.just(throwable))
                .flatMap(combinedAuthResponse -> loginAPI.getAccountDetails(
                        combinedAuthResponse.getSessionId()))
                .compose(RxUtils.applySingleSchedulers());
    }

}
