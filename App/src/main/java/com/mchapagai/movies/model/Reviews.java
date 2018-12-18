package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Reviews implements Parcelable {

    @SerializedName("id")
    private String reviewId;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String reviewContent;
    @SerializedName("url")
    private String url;

    protected Reviews(Parcel in) {
        reviewId = in.readString();
        author = in.readString();
        reviewContent = in.readString();
        url = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getReviewId() {
        return reviewId;
    }


    public String getAuthor() {
        return author;
    }


    public String getReviewContent() {
        return reviewContent;
    }


    public String getUrl() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewId);
        dest.writeString(author);
        dest.writeString(reviewContent);
        dest.writeString(url);
    }
}
