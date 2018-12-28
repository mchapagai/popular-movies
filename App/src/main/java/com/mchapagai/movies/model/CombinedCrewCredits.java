package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.library.utils.DateTimeUtils;

public class CombinedCrewCredits implements Parcelable {

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

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("credit_id")
	private String creditId;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("id")
	private int id;

	@SerializedName("department")
	private String department;

	@SerializedName("job")
	private String job;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	protected CombinedCrewCredits(Parcel in) {
		overview = in.readString();
		originalLanguage = in.readString();
		originalTitle = in.readString();
		video = in.readByte() != 0;
		title = in.readString();
		posterPath = in.readString();
		backdropPath = in.readString();
		mediaType = in.readString();
		releaseDate = in.readString();
		creditId = in.readString();
		voteAverage = in.readDouble();
		popularity = in.readDouble();
		id = in.readInt();
		department = in.readString();
		job = in.readString();
		adult = in.readByte() != 0;
		voteCount = in.readInt();
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
		dest.writeString(creditId);
		dest.writeDouble(voteAverage);
		dest.writeDouble(popularity);
		dest.writeInt(id);
		dest.writeString(department);
		dest.writeString(job);
		dest.writeByte((byte) (adult ? 1 : 0));
		dest.writeInt(voteCount);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<CombinedCrewCredits> CREATOR = new Creator<CombinedCrewCredits>() {
		@Override
		public CombinedCrewCredits createFromParcel(Parcel in) {
			return new CombinedCrewCredits(in);
		}

		@Override
		public CombinedCrewCredits[] newArray(int size) {
			return new CombinedCrewCredits[size];
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
        String date = null;
        if (!TextUtils.isEmpty(releaseDate)) {
            date = DateTimeUtils.getYearOnly(releaseDate);
        }
        return date;
	}

	public String getCreditId() {
		return creditId;
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

	public String getDepartment() {
		return department;
	}

	public String getJob() {
		return job;
	}

	public boolean isAdult() {
		return adult;
	}

	public int getVoteCount() {
		return voteCount;
	}

}