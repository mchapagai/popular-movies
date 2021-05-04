package com.mchapagai.klutter.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mchapagai.klutter.model.Movies;

import java.util.List;

public class MovieResponse implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movies> movies;


    protected MovieResponse(Parcel in) {
        page = in.readInt();
        movies = in.createTypedArrayList(Movies.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(movies);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public static Creator<MovieResponse> getCREATOR() {
        return CREATOR;
    }
}