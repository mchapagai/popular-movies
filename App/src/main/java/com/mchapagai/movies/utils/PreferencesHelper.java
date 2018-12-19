package com.mchapagai.movies.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mchapagai.movies.common.Constants;


public class PreferencesHelper {

    private final String POPULAR_MOVIES_SHARED_PREF = "popularMoviesSharedPreferences";
    private final String PREF_USER_NAME = "userName";
    private final String PREF_USER_ID = "userId";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(POPULAR_MOVIES_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String setSessionKey() {
        return sharedPreferences.getString(Constants.PREF_SESSION_ID, null);
    }

    public void setAccessToken(String token) {
        editor.putString(Constants.PREF_ACCESS_TOKEN, token);
        editor.commit();
        editor.putBoolean(Constants.PREF_ACCESS_TOKEN_TAKEN, true);
        editor.commit();
    }

    public void setAccessTokenFalse() {
        editor.putBoolean(Constants.PREF_ACCESS_TOKEN_TAKEN, false);
        editor.commit();
    }

    public void setAccessTokenVerifiedFalse() {
        editor.putBoolean(Constants.PREF_ACCESS_TOKEN_VERIFIED, false);
        editor.commit();
    }

    public void setAccessTokenVerified() {
        editor.putBoolean(Constants.PREF_ACCESS_TOKEN_VERIFIED, true);
        editor.commit();
    }

    public void setUserSessionId(String id) {
        editor.putString(Constants.PREF_SESSION_ID, id);
        editor.commit();
        editor.putBoolean(Constants.PREF_SESSION_ID_GRANTED, true);
        editor.commit();
        editor.putBoolean(Constants.PREF_SIGNED_IN, true);
        editor.commit();
    }

    public void setAccountDetails(String id, String username) {
        editor.putString(PREF_USER_ID, id);
        editor.commit();
        editor.putBoolean(Constants.PREF_GET_ACCOUNT_ID, true);
        editor.commit();
        editor.putString(PREF_USER_NAME, username);
        editor.commit();
    }

    public void setAccountIDFalse() {
        editor.putBoolean(Constants.PREF_GET_ACCOUNT_ID, false);
        editor.commit();
    }

    public boolean isSignedIn() {
        return sharedPreferences.getBoolean(Constants.PREF_SIGNED_IN, false);
    }

    public void setSignedOut(){
        editor.putBoolean(Constants.PREF_SIGNED_IN, false);
        editor.commit();
        editor.putBoolean(Constants.PREF_ACCESS_TOKEN_TAKEN, false);
        editor.commit();
        editor.putBoolean(Constants.PREF_SESSION_ID_GRANTED,false);
        editor.commit();
    }
}
