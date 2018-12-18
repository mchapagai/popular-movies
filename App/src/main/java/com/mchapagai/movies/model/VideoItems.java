package com.mchapagai.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class VideoItems implements Parcelable {

    private static final String SITE_YOUTUBE = "YouTube";

	@SerializedName("site")
	private String site;

	@SerializedName("size")
	private int size;

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("key")
	private String key;

    protected VideoItems(Parcel in) {
        site = in.readString();
        size = in.readInt();
        iso31661 = in.readString();
        name = in.readString();
        id = in.readString();
        type = in.readString();
        iso6391 = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(iso31661);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(iso6391);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoItems> CREATOR = new Creator<VideoItems>() {
        @Override
        public VideoItems createFromParcel(Parcel in) {
            return new VideoItems(in);
        }

        @Override
        public VideoItems[] newArray(int size) {
            return new VideoItems[size];
        }
    };

    public boolean isYoutubeVideo() {
        return site.toLowerCase(Locale.US).equals(SITE_YOUTUBE.toLowerCase(Locale.US));
    }

    public static String getSiteYoutube() {
        return SITE_YOUTUBE;
    }

	public String getSite() {
		return site;
	}

	public int getSize() {
		return size;
	}

	public String getIso31661() {
		return iso31661;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getIso6391() {
		return iso6391;
	}

	public String getKey() {
		return key;
	}
}