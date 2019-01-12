package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class LastEpisodeToAir implements Parcelable {

	@SerializedName("production_code")
	private String productionCode;

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_number")
	private int episodeNumber;

	@SerializedName("show_id")
	private int showId;

	@SerializedName("vote_average")
	private int voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("still_path")
	private String stillPath;

	@SerializedName("vote_count")
	private int voteCount;

	protected LastEpisodeToAir(Parcel in) {
		productionCode = in.readString();
		airDate = in.readString();
		overview = in.readString();
		episodeNumber = in.readInt();
		showId = in.readInt();
		voteAverage = in.readInt();
		name = in.readString();
		seasonNumber = in.readInt();
		id = in.readInt();
		stillPath = in.readString();
		voteCount = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(productionCode);
		dest.writeString(airDate);
		dest.writeString(overview);
		dest.writeInt(episodeNumber);
		dest.writeInt(showId);
		dest.writeInt(voteAverage);
		dest.writeString(name);
		dest.writeInt(seasonNumber);
		dest.writeInt(id);
		dest.writeString(stillPath);
		dest.writeInt(voteCount);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<LastEpisodeToAir> CREATOR = new Creator<LastEpisodeToAir>() {
		@Override
		public LastEpisodeToAir createFromParcel(Parcel in) {
			return new LastEpisodeToAir(in);
		}

		@Override
		public LastEpisodeToAir[] newArray(int size) {
			return new LastEpisodeToAir[size];
		}
	};

	public String getProductionCode() {
		return productionCode;
	}

	public String getAirDate() {
		return airDate;
	}

	public String getOverview() {
		return overview;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public int getShowId() {
		return showId;
	}

	public int getVoteAverage() {
		return voteAverage;
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

	public String getStillPath() {
		return stillPath;
	}

	public int getVoteCount() {
		return voteCount;
	}
}