package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.Videos;
import java.util.List;

public class VideoResponse implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Videos> videos;

    protected VideoResponse(Parcel in) {
        id = in.readInt();
        videos = in.createTypedArrayList(Videos.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(videos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoResponse> CREATOR = new Creator<VideoResponse>() {
        @Override
        public VideoResponse createFromParcel(Parcel in) {
            return new VideoResponse(in);
        }

        @Override
        public VideoResponse[] newArray(int size) {
            return new VideoResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public List<Videos> getVideos() {
        return videos;
    }
}