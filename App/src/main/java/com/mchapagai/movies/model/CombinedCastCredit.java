package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedCastCredit implements Parcelable {

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("episode_count")
	private int episodeCount;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("character")
	private String character;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("credit_id")
	private String creditId;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("adult")
	private boolean adult;

    protected CombinedCastCredit(Parcel in) {
        firstAirDate = in.readString();
        overview = in.readString();
        originalLanguage = in.readString();
        episodeCount = in.readInt();
        posterPath = in.readString();
        originCountry = in.createStringArrayList();
        backdropPath = in.readString();
        character = in.readString();
        mediaType = in.readString();
        creditId = in.readString();
        originalName = in.readString();
        popularity = in.readDouble();
        voteAverage = in.readDouble();
        name = in.readString();
        id = in.readInt();
        voteCount = in.readInt();
        originalTitle = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        releaseDate = in.readString();
        adult = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstAirDate);
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeInt(episodeCount);
        dest.writeString(posterPath);
        dest.writeStringList(originCountry);
        dest.writeString(backdropPath);
        dest.writeString(character);
        dest.writeString(mediaType);
        dest.writeString(creditId);
        dest.writeString(originalName);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(voteCount);
        dest.writeString(originalTitle);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeByte((byte) (adult ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CombinedCastCredit> CREATOR = new Creator<CombinedCastCredit>() {
        @Override
        public CombinedCastCredit createFromParcel(Parcel in) {
            return new CombinedCastCredit(in);
        }

        @Override
        public CombinedCastCredit[] newArray(int size) {
            return new CombinedCastCredit[size];
        }
    };

    public String getFirstAirDate() {
		return firstAirDate;
	}

	public String getOverview() {
		return overview;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public List<Integer> getGenreIds() {
		return genreIds;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public List<String> getOriginCountry() {
		return originCountry;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public String getCharacter() {
		return character;
	}

	public String getMediaType() {
		return mediaType;
	}

	public String getCreditId() {
		return creditId;
	}

	public String getOriginalName() {
		return originalName;
	}

	public double getPopularity() {
		return popularity;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getVoteCount() {
		return voteCount;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public boolean isAdult() {
		return adult;
	}
}