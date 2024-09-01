package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ShowsSeason implements Parcelable {

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_count")
	private int episodeCount;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private String posterPath;

	protected ShowsSeason(Parcel in) {
		airDate = in.readString();
		overview = in.readString();
		episodeCount = in.readInt();
		name = in.readString();
		seasonNumber = in.readInt();
		id = in.readInt();
		posterPath = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(airDate);
		dest.writeString(overview);
		dest.writeInt(episodeCount);
		dest.writeString(name);
		dest.writeInt(seasonNumber);
		dest.writeInt(id);
		dest.writeString(posterPath);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ShowsSeason> CREATOR = new Creator<ShowsSeason>() {
		@Override
		public ShowsSeason createFromParcel(Parcel in) {
			return new ShowsSeason(in);
		}

		@Override
		public ShowsSeason[] newArray(int size) {
			return new ShowsSeason[size];
		}
	};

	public String getAirDate() {
		return airDate;
	}

	public String getOverview() {
		return overview;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public String getName() {
		return name;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public int getId() {
		return id;
	}

	public String getPosterPath() {
		return posterPath;
	}
}