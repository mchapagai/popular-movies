package com.mchapagai.klutter.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.klutter.utils.DateTimeUtils;
import java.util.List;
import java.util.Objects;

public class CombinedCastCredit implements Parcelable {

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("episode_count")
    private int episodeCount;

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
        String date = null;
        if (!TextUtils.isEmpty(releaseDate)) {
            date = DateTimeUtils.getYearOnly(releaseDate);
        }
        return date;
    }

    public boolean isAdult() {
        return adult;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CombinedCastCredit that = (CombinedCastCredit) o;
        return episodeCount == that.episodeCount &&
                Double.compare(that.popularity, popularity) == 0 &&
                Double.compare(that.voteAverage, voteAverage) == 0 &&
                id == that.id &&
                voteCount == that.voteCount &&
                video == that.video &&
                adult == that.adult &&
                Objects.equals(firstAirDate, that.firstAirDate) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(originalLanguage, that.originalLanguage) &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(originCountry, that.originCountry) &&
                Objects.equals(backdropPath, that.backdropPath) &&
                Objects.equals(character, that.character) &&
                Objects.equals(mediaType, that.mediaType) &&
                Objects.equals(creditId, that.creditId) &&
                Objects.equals(originalName, that.originalName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(originalTitle, that.originalTitle) &&
                Objects.equals(title, that.title) &&
                Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {

        return Objects
                .hash(firstAirDate, overview, originalLanguage, episodeCount, posterPath, originCountry, backdropPath,
                        character, mediaType, creditId, originalName, popularity, voteAverage, name, id, voteCount,
                        originalTitle, video, title, releaseDate, adult);
    }
}