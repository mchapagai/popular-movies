package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.People;

import java.util.List;

public class PopularPersonResponse implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private List<People> people;

	protected PopularPersonResponse(Parcel in) {
		page = in.readInt();
		people = in.createTypedArrayList(People.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(page);
		dest.writeTypedList(people);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PopularPersonResponse> CREATOR = new Creator<PopularPersonResponse>() {
		@Override
		public PopularPersonResponse createFromParcel(Parcel in) {
			return new PopularPersonResponse(in);
		}

		@Override
		public PopularPersonResponse[] newArray(int size) {
			return new PopularPersonResponse[size];
		}
	};

	public int getPage() {
		return page;
	}

	public List<People> getPeople() {
		return people;
	}
}