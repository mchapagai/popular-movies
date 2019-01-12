package com.mchapagai.movies.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AuthToken implements Parcelable {

    @SerializedName("request_token")
    private String requestToken;

    protected AuthToken(Parcel in) {
        requestToken = in.readString();
    }

    public static final Creator<AuthToken> CREATOR = new Creator<AuthToken>() {
        @Override
        public AuthToken createFromParcel(Parcel in) {
            return new AuthToken(in);
        }

        @Override
        public AuthToken[] newArray(int size) {
            return new AuthToken[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestToken);
    }

    public String getRequestToken() {
        return requestToken;
    }
}