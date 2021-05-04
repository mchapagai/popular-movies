package com.mchapagai.klutter.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Gravatar implements Parcelable {

    @SerializedName("hash")
    private String hash;

    protected Gravatar(Parcel in) {
        hash = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hash);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Gravatar> CREATOR = new Creator<Gravatar>() {
        @Override
        public Gravatar createFromParcel(Parcel in) {
            return new Gravatar(in);
        }

        @Override
        public Gravatar[] newArray(int size) {
            return new Gravatar[size];
        }
    };

    public String getHash() {
        return hash;
    }
}