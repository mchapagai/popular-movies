package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Genres implements Parcelable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	protected Genres(Parcel in) {
		name = in.readString();
		id = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Genres> CREATOR = new Creator<Genres>() {
		@Override
		public Genres createFromParcel(Parcel in) {
			return new Genres(in);
		}

		@Override
		public Genres[] newArray(int size) {
			return new Genres[size];
		}
	};

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}