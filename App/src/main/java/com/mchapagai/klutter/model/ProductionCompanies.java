package com.mchapagai.klutter.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ProductionCompanies implements Parcelable {

	@SerializedName("logo_path")
	private String logoPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	protected ProductionCompanies(Parcel in) {
		logoPath = in.readString();
		name = in.readString();
		id = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(logoPath);
		dest.writeString(name);
		dest.writeInt(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ProductionCompanies> CREATOR = new Creator<ProductionCompanies>() {
		@Override
		public ProductionCompanies createFromParcel(Parcel in) {
			return new ProductionCompanies(in);
		}

		@Override
		public ProductionCompanies[] newArray(int size) {
			return new ProductionCompanies[size];
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

}