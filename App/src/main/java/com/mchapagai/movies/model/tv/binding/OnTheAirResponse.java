package com.mchapagai.movies.model.tv.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.tv.OnTheAir;
import java.util.List;

public class OnTheAirResponse implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<OnTheAir> onTheAir;


    protected OnTheAirResponse(Parcel in) {
        page = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OnTheAirResponse> CREATOR = new Creator<OnTheAirResponse>() {
        @Override
        public OnTheAirResponse createFromParcel(Parcel in) {
            return new OnTheAirResponse(in);
        }

        @Override
        public OnTheAirResponse[] newArray(int size) {
            return new OnTheAirResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public List<OnTheAir> getOnTheAir() {
        return onTheAir;
    }
}