package com.mchapagai.movies.model.movies.binding;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.movies.VideoItems;

public class VideoResponse implements Parcelable {

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<VideoItems> videos;

	protected VideoResponse(Parcel in) {
		id = in.readInt();
		videos = in.createTypedArrayList(VideoItems.CREATOR);
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

	public List<VideoItems> getVideos() {
		return videos;
	}
}