package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CastCredit implements Parcelable {

    @SerializedName("cast_id")
    private int castId;

    @SerializedName("character")
    private String character;

    @SerializedName("gender")
    private int gender;

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("id")
    private int id;

    @SerializedName("order")
    private int order;

    protected CastCredit(Parcel in) {
        castId = in.readInt();
        character = in.readString();
        gender = in.readInt();
        creditId = in.readString();
        name = in.readString();
        profilePath = in.readString();
        id = in.readInt();
        order = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(castId);
        dest.writeString(character);
        dest.writeInt(gender);
        dest.writeString(creditId);
        dest.writeString(name);
        dest.writeString(profilePath);
        dest.writeInt(id);
        dest.writeInt(order);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CastCredit> CREATOR = new Creator<CastCredit>() {
        @Override
        public CastCredit createFromParcel(Parcel in) {
            return new CastCredit(in);
        }

        @Override
        public CastCredit[] newArray(int size) {
            return new CastCredit[size];
        }
    };

    public int getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public int getGender() {
        return gender;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }
}