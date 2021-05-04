package com.mchapagai.klutter.model.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.klutter.model.Genres;
import com.mchapagai.klutter.model.ProductionCompanies;
import com.mchapagai.klutter.model.LastEpisodeToAir;
import com.mchapagai.klutter.model.NextEpisodeToAir;
import com.mchapagai.klutter.model.ShowsCreatedBy;
import com.mchapagai.klutter.model.ShowsSeason;
import com.mchapagai.klutter.model.ShowsNetwork;
import java.util.List;

public class ShowsDetailsResponse implements Parcelable {

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("number_of_episodes")
	private int numberOfEpisodes;

	@SerializedName("videos")
	private VideoResponse videos;

	@SerializedName("networks")
	private List<ShowsNetwork> networks;

	@SerializedName("type")
	private String type;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("genres")
	private List<Genres> genres;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("id")
	private int id;

	@SerializedName("number_of_seasons")
	private int numberOfSeasons;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("seasons")
	private List<ShowsSeason> seasons;

	@SerializedName("languages")
	private List<String> languages;

	@SerializedName("created_by")
	private List<ShowsCreatedBy> createdBy;

	@SerializedName("last_episode_to_air")
	private LastEpisodeToAir lastEpisodeToAir;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("production_companies")
	private List<ProductionCompanies> productionCompanies;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("episode_run_time")
	private List<Integer> episodeRunTime;

	@SerializedName("next_episode_to_air")
	private NextEpisodeToAir nextEpisodeToAir;

	@SerializedName("in_production")
	private boolean inProduction;

	@SerializedName("last_air_date")
	private String lastAirDate;

	@SerializedName("homepage")
	private String homepage;

	@SerializedName("status")
	private String status;

	protected ShowsDetailsResponse(Parcel in) {
		originalLanguage = in.readString();
		numberOfEpisodes = in.readInt();
		type = in.readString();
		backdropPath = in.readString();
		popularity = in.readDouble();
		id = in.readInt();
		numberOfSeasons = in.readInt();
		voteCount = in.readInt();
		firstAirDate = in.readString();
		overview = in.readString();
		languages = in.createStringArrayList();
		posterPath = in.readString();
		originCountry = in.createStringArrayList();
		originalName = in.readString();
		voteAverage = in.readDouble();
		name = in.readString();
		inProduction = in.readByte() != 0;
		lastAirDate = in.readString();
		homepage = in.readString();
		status = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(originalLanguage);
		dest.writeInt(numberOfEpisodes);
		dest.writeString(type);
		dest.writeString(backdropPath);
		dest.writeDouble(popularity);
		dest.writeInt(id);
		dest.writeInt(numberOfSeasons);
		dest.writeInt(voteCount);
		dest.writeString(firstAirDate);
		dest.writeString(overview);
		dest.writeStringList(languages);
		dest.writeString(posterPath);
		dest.writeStringList(originCountry);
		dest.writeString(originalName);
		dest.writeDouble(voteAverage);
		dest.writeString(name);
		dest.writeByte((byte) (inProduction ? 1 : 0));
		dest.writeString(lastAirDate);
		dest.writeString(homepage);
		dest.writeString(status);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ShowsDetailsResponse> CREATOR = new Creator<ShowsDetailsResponse>() {
		@Override
		public ShowsDetailsResponse createFromParcel(Parcel in) {
			return new ShowsDetailsResponse(in);
		}

		@Override
		public ShowsDetailsResponse[] newArray(int size) {
			return new ShowsDetailsResponse[size];
		}
	};

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public int getNumberOfEpisodes() {
		return numberOfEpisodes;
	}

	public VideoResponse getVideos() {
		return videos;
	}

	public List<ShowsNetwork> getNetworks() {
		return networks;
	}

	public String getType() {
		return type;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public List<Genres> getGenres() {
		return genres;
	}

	public double getPopularity() {
		return popularity;
	}

	public int getId() {
		return id;
	}

	public int getNumberOfSeasons() {
		return numberOfSeasons;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public String getFirstAirDate() {
		return firstAirDate;
	}

	public String getOverview() {
		return overview;
	}

	public List<ShowsSeason> getSeasons() {
		return seasons;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public List<ShowsCreatedBy> getCreatedBy() {
		return createdBy;
	}

	public LastEpisodeToAir getLastEpisodeToAir() {
		return lastEpisodeToAir;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public List<String> getOriginCountry() {
		return originCountry;
	}

	public List<ProductionCompanies> getProductionCompanies() {
		return productionCompanies;
	}

	public String getOriginalName() {
		return originalName;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getEpisodeRunTime() {
		return episodeRunTime;
	}

	public NextEpisodeToAir getNextEpisodeToAir() {
		return nextEpisodeToAir;
	}

	public boolean isInProduction() {
		return inProduction;
	}

	public String getLastAirDate() {
		return lastAirDate;
	}

	public String getHomepage() {
		return homepage;
	}

	public String getStatus() {
		return status;
	}
}