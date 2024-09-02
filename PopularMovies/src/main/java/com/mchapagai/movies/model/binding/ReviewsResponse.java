package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.Reviews;
import java.util.List;

public class ReviewsResponse implements Parcelable {

    @SerializedName("results")
    private List<Reviews> reviews;
    @SerializedName("id")
    private int movieId;

    protected ReviewsResponse(Parcel in) {
        reviews = in.createTypedArrayList(Reviews.CREATOR);
        movieId = in.readInt();
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
        dest.writeInt(movieId);
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public int getMovieId() {
        return movieId;
    }

    public static Creator<ReviewsResponse> getCREATOR() {
        return CREATOR;
    }
}
