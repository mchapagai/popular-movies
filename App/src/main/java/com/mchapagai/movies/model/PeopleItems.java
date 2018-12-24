package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleItems implements Parcelable {

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("name")
	private String name;

	protected PeopleItems(Parcel in) {
		overview = in.readString();
		originalLanguage = in.readString();
		originalTitle = in.readString();
		video = in.readByte() != 0;
		title = in.readString();
		posterPath = in.readString();
		backdropPath = in.readString();
		mediaType = in.readString();
		releaseDate = in.readString();
		voteAverage = in.readDouble();
		popularity = in.readDouble();
		id = in.readInt();
		adult = in.readByte() != 0;
		voteCount = in.readInt();
		firstAirDate = in.readString();
		originCountry = in.createStringArrayList();
		originalName = in.readString();
		name = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(overview);
		dest.writeString(originalLanguage);
		dest.writeString(originalTitle);
		dest.writeByte((byte) (video ? 1 : 0));
		dest.writeString(title);
		dest.writeString(posterPath);
		dest.writeString(backdropPath);
		dest.writeString(mediaType);
		dest.writeString(releaseDate);
		dest.writeDouble(voteAverage);
		dest.writeDouble(popularity);
		dest.writeInt(id);
		dest.writeByte((byte) (adult ? 1 : 0));
		dest.writeInt(voteCount);
		dest.writeString(firstAirDate);
		dest.writeStringList(originCountry);
		dest.writeString(originalName);
		dest.writeString(name);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PeopleItems> CREATOR = new Creator<PeopleItems>() {
		@Override
		public PeopleItems createFromParcel(Parcel in) {
			return new PeopleItems(in);
		}

		@Override
		public PeopleItems[] newArray(int size) {
			return new PeopleItems[size];
		}
	};

	public String getOverview() {
		return overview;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public boolean isVideo() {
		return video;
	}

	public String getTitle() {
		return title;
	}

	public List<Integer> getGenreIds() {
		return genreIds;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public String getMediaType() {
		return mediaType;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public double getPopularity() {
		return popularity;
	}

	public int getId() {
		return id;
	}

	public boolean isAdult() {
		return adult;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public String getFirstAirDate() {
		return firstAirDate;
	}

	public List<String> getOriginCountry() {
		return originCountry;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getName() {
		return name;
	}
}