package com.mchapagai.movies.model.movies;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class CrewCredits implements Parcelable {

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("id")
    private int id;

    @SerializedName("department")
    private String department;

    @SerializedName("job")
    private String job;

    protected CrewCredits(Parcel in) {
        creditId = in.readString();
        name = in.readString();
        profilePath = in.readString();
        id = in.readInt();
        department = in.readString();
        job = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(creditId);
        dest.writeString(name);
        dest.writeString(profilePath);
        dest.writeInt(id);
        dest.writeString(department);
        dest.writeString(job);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CrewCredits> CREATOR = new Creator<CrewCredits>() {
        @Override
        public CrewCredits createFromParcel(Parcel in) {
            return new CrewCredits(in);
        }

        @Override
        public CrewCredits[] newArray(int size) {
            return new CrewCredits[size];
        }
    };

    public String getCreditId() {
        return creditId;
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

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }
}