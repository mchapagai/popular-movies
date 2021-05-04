package com.mchapagai.klutter.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ShowsCreatedBy implements Parcelable {

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

	protected ShowsCreatedBy(Parcel in) {
		gender = in.readInt();
		creditId = in.readString();
		name = in.readString();
		profilePath = in.readString();
		id = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(gender);
		dest.writeString(creditId);
		dest.writeString(name);
		dest.writeString(profilePath);
		dest.writeInt(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ShowsCreatedBy> CREATOR = new Creator<ShowsCreatedBy>() {
		@Override
		public ShowsCreatedBy createFromParcel(Parcel in) {
			return new ShowsCreatedBy(in);
		}

		@Override
		public ShowsCreatedBy[] newArray(int size) {
			return new ShowsCreatedBy[size];
		}
	};

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
}