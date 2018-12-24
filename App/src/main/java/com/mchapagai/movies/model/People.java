package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class People implements Parcelable {

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("known_for")
    private List<PeopleItems> peopleItems;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    protected People(Parcel in) {
        popularity = in.readDouble();
        peopleItems = in.createTypedArrayList(PeopleItems.CREATOR);
        name = in.readString();
        profilePath = in.readString();
        id = in.readInt();
        adult = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeTypedList(peopleItems);
        dest.writeString(name);
        dest.writeString(profilePath);
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public List<PeopleItems> getPeopleItems() {
        return peopleItems;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }
}