package com.mchapagai.klutter.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class NextEpisodeToAir implements Parcelable {

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
	private Object stillPath;

	@SerializedName("vote_count")
	private int voteCount;

	protected NextEpisodeToAir(Parcel in) {
		productionCode = in.readString();
		airDate = in.readString();
		overview = in.readString();
		episodeNumber = in.readInt();
		showId = in.readInt();
		voteAverage = in.readInt();
		name = in.readString();
		seasonNumber = in.readInt();
		id = in.readInt();
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
		dest.writeInt(voteCount);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<NextEpisodeToAir> CREATOR = new Creator<NextEpisodeToAir>() {
		@Override
		public NextEpisodeToAir createFromParcel(Parcel in) {
			return new NextEpisodeToAir(in);
		}

		@Override
		public NextEpisodeToAir[] newArray(int size) {
			return new NextEpisodeToAir[size];
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

	public Object getStillPath() {
		return stillPath;
	}

	public int getVoteCount() {
		return voteCount;
	}
}