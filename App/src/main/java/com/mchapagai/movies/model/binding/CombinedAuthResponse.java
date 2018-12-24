package com.mchapagai.movies.model.binding;

import com.mchapagai.movies.model.AuthSession;
import com.mchapagai.movies.model.AuthToken;

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
