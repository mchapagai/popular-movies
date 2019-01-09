package com.mchapagai.movies.common;

public class Constants {

    public static final int SPLASH_TIME_OUT = 3000;

    public static final String SERVICE_ENDPOINT = "https://api.themoviedb.org/3/";

    // Shared Preferences
    public static final String PREF_SESSION_ID = "sessionId";
    public static final String PREF_ACCESS_TOKEN = "accessToken";
    public static final String PREF_ACCESS_TOKEN_TAKEN = "accessTokenTaken";
    public static final String PREF_ACCESS_TOKEN_VERIFIED = "accessTokenVerified";
    public static final String PREF_SESSION_ID_GRANTED = "SessionIdGranted";
    public static final String PREF_SIGNED_IN = "signedIn";
    public static final String PREF_GET_ACCOUNT_ID = "accountId";
    public static final String PERSON_ID_INTENT = "profile_id";

    // Movie Details
    public static final String MOVIE_DETAILS = "MOVIE_DETAILS_INTENT";
    public static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";

    public static final float SCRIM_ADJUSTMENT = 0.075f;
    public static final String SECURE_IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/w500/";
    public static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/w342";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

}
