package com.mchapagai.movies.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AccountDetails implements Parcelable {

	@SerializedName("include_adult")
	private boolean includeAdult;

	@SerializedName("name")
	private String name;

	@SerializedName("avatar")
	private Avatar avatar;

	@SerializedName("sessionId")
	private int id;

	@SerializedName("username")
	private String username;

    private AccountDetails(Parcel in) {
        includeAdult = in.readByte() != 0;
        name = in.readString();
        id = in.readInt();
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


    public String getName() {
        return name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
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
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(username);
    }
}