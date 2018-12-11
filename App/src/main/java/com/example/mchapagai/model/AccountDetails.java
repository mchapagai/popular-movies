package com.example.mchapagai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AccountDetails implements Parcelable {

	@SerializedName("include_adult")
	private boolean includeAdult;

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@SerializedName("avatar")
	private Avatar avatar;

	@SerializedName("id")
	private int id;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("username")
	private String username;

    protected AccountDetails(Parcel in) {
        includeAdult = in.readByte() != 0;
        iso31661 = in.readString();
        name = in.readString();
        id = in.readInt();
        iso6391 = in.readString();
        username = in.readString();
    }

    public static final Creator<AccountDetails> CREATOR = new Creator<AccountDetails>() {
        @Override
        public AccountDetails createFromParcel(Parcel in) {
            return new AccountDetails(in);
        }

        @Override
        public AccountDetails[] newArray(int size) {
            return new AccountDetails[size];
        }
    };

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public String getIso31661() {
        return iso31661;
    }

    public String getName() {
        return name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (includeAdult ? 1 : 0));
        dest.writeString(iso31661);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(iso6391);
        dest.writeString(username);
    }
}