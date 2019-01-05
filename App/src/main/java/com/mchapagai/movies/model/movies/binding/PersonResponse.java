package com.mchapagai.movies.model.movies.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonResponse implements Parcelable {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("also_known_as")
    private List<String> alsoKnownAs;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("known_for_department")
    private String knownForDepartment;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("biography")
    private String biography;

    @SerializedName("place_of_birth")
    private Object placeOfBirth;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int personId;

    @SerializedName("homepage")
    private String homepage;

    protected PersonResponse(Parcel in) {
        adult = in.readByte() != 0;
        birthday = in.readString();
        alsoKnownAs = in.createStringArrayList();
        imdbId = in.readString();
        knownForDepartment = in.readString();
        profilePath = in.readString();
        biography = in.readString();
        name = in.readString();
        personId = in.readInt();
        homepage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(birthday);
        dest.writeStringList(alsoKnownAs);
        dest.writeString(imdbId);
        dest.writeString(knownForDepartment);
        dest.writeString(profilePath);
        dest.writeString(biography);
        dest.writeString(name);
        dest.writeInt(personId);
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

    public boolean isAdult() {
        return adult;
    }

    public String getBirthday() {
        return birthday;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
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

    public Object getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getName() {
        return name;
    }

    public int getPersonId() {
        return personId;
    }

    public String getHomepage() {
        return homepage;
    }
}