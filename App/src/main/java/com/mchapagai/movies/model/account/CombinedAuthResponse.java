package com.mchapagai.movies.model.account;

public class CombinedAuthResponse {

    private AuthToken authToken;
    private AuthSession authSession;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public AuthSession getAuthSession() {
        return authSession;
    }

    public CombinedAuthResponse(AuthToken authToken, AuthSession authSession) {
        this.authToken = authToken;
        this.authSession = authSession;
    }
}
