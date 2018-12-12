package com.example.mchapagai.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mchapagai.model.Movies;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<Movies> movies;

	@SerializedName("total_results")
	private int totalResults;


	protected MovieResponse(Parcel in) {
		page = in.readInt();
		totalPages = in.readInt();
		movies = in.createTypedArrayList(Movies.CREATOR);
		totalResults = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(page);
		dest.writeInt(totalPages);
		dest.writeTypedList(movies);
		dest.writeInt(totalResults);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
		@Override
		public MovieResponse createFromParcel(Parcel in) {
			return new MovieResponse(in);
		}

		@Override
		public MovieResponse[] newArray(int size) {
			return new MovieResponse[size];
		}
	};

	public int getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<Movies> getMovies() {
		return movies;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public static Creator<MovieResponse> getCREATOR() {
		return CREATOR;
	}
}