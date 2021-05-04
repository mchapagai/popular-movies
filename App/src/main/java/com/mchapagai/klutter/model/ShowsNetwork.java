package com.mchapagai.klutter.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ShowsNetwork implements Parcelable {

	@SerializedName("logo_path")
	private String logoPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("origin_country")
	private String originCountry;

	protected ShowsNetwork(Parcel in) {
		logoPath = in.readString();
		name = in.readString();
		id = in.readInt();
		originCountry = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(logoPath);
		dest.writeString(name);
		dest.writeInt(id);
		dest.writeString(originCountry);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ShowsNetwork> CREATOR = new Creator<ShowsNetwork>() {
		@Override
		public ShowsNetwork createFromParcel(Parcel in) {
			return new ShowsNetwork(in);
		}

		@Override
		public ShowsNetwork[] newArray(int size) {
			return new ShowsNetwork[size];
		}
	};

	public String getLogoPath() {
		return logoPath;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getOriginCountry() {
		return originCountry;
	}
}