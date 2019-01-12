package com.mchapagai.movies.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AuthSession implements Parcelable {

    @SerializedName("session_id")
    private String sessionId;

    private AuthSession(Parcel in) {
        sessionId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sessionId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthSession> CREATOR = new Creator<AuthSession>() {
        @Override
        public AuthSession createFromParcel(Parcel in) {
            return new AuthSession(in);
        }

        @Override
        public AuthSession[] newArray(int size) {
            return new AuthSession[size];
        }
    };

    public String getSessionId() {
        return sessionId;
    }
}
