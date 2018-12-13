package com.example.mchapagai.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mchapagai.model.Reviews;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse implements Parcelable {

    @SerializedName("results")
    private List<Reviews> reviews;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("id")
    private int movieId;
    @SerializedName("page")
    private int page;

    protected ReviewsResponse(Parcel in) {
        reviews = in.createTypedArrayList(Reviews.CREATOR);
        totalPages = in.readInt();
        movieId = in.readInt();
        page = in.readInt();
    }

    public static final Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {
        @Override
        public ReviewsResponse createFromParcel(Parcel in) {
            return new ReviewsResponse(in);
        }

        @Override
        public ReviewsResponse[] newArray(int size) {
            return new ReviewsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(reviews);
        dest.writeInt(totalPages);
        dest.writeInt(movieId);
        dest.writeInt(page);
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getPage() {
        return page;
    }

    public static Creator<ReviewsResponse> getCREATOR() {
        return CREATOR;
    }
}
