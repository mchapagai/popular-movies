package com.example.mchapagai.model.binding;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class PersonResponse implements Parcelable {

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("also_known_as")
	private List<String> alsoKnownAs;

	@SerializedName("gender")
	private int gender;

	@SerializedName("imdb_id")
	private String imdbId;

	@SerializedName("known_for_department")
	private String knownForDepartment;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("biography")
	private String biography;

	@SerializedName("deathday")
	private Object deathday;

	@SerializedName("place_of_birth")
	private Object placeOfBirth;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("homepage")
	private String homepage;

	public PersonResponse(Parcel in) {
		birthday = in.readString();
		alsoKnownAs = in.createStringArrayList();
		gender = in.readInt();
		imdbId = in.readString();
		knownForDepartment = in.readString();
		profilePath = in.readString();
		biography = in.readString();
		popularity = in.readDouble();
		name = in.readString();
		id = in.readInt();
		adult = in.readByte() != 0;
		homepage = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(birthday);
		dest.writeStringList(alsoKnownAs);
		dest.writeInt(gender);
		dest.writeString(imdbId);
		dest.writeString(knownForDepartment);
		dest.writeString(profilePath);
		dest.writeString(biography);
		dest.writeDouble(popularity);
		dest.writeString(name);
		dest.writeInt(id);
		dest.writeByte((byte) (adult ? 1 : 0));
		dest.writeString(homepage);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PersonResponse> CREATOR = new Creator<PersonResponse>() {
		@Override
		public PersonResponse createFromParcel(Parcel in) {
			return new PersonResponse(in);
		}

		@Override
		public PersonResponse[] newArray(int size) {
			return new PersonResponse[size];
		}
	};

	public String getBirthday() {
		return birthday;
	}

	public List<String> getAlsoKnownAs() {
		return alsoKnownAs;
	}

	public int getGender() {
		return gender;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String getKnownForDepartment() {
		return knownForDepartment;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public String getBiography() {
		return biography;
	}

	public Object getDeathday() {
		return deathday;
	}

	public Object getPlaceOfBirth() {
		return placeOfBirth;
	}

	public double getPopularity() {
		return popularity;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public boolean isAdult() {
		return adult;
	}

	public String getHomepage() {
		return homepage;
	}
}