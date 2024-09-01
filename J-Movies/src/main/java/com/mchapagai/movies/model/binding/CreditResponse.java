package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.CastCredit;
import com.mchapagai.movies.model.CrewCredits;

import java.util.List;

public class CreditResponse implements Parcelable {

    @SerializedName("cast")
    private List<CastCredit> cast;

    @SerializedName("id")
    private int id;

    @SerializedName("crew")
    private List<CrewCredits> crew;

    protected CreditResponse(Parcel in) {
        cast = in.createTypedArrayList(CastCredit.CREATOR);
        id = in.readInt();
        crew = in.createTypedArrayList(CrewCredits.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cast);
        dest.writeInt(id);
        dest.writeTypedList(crew);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreditResponse> CREATOR = new Creator<CreditResponse>() {
        @Override
        public CreditResponse createFromParcel(Parcel in) {
            return new CreditResponse(in);
        }

        @Override
        public CreditResponse[] newArray(int size) {
            return new CreditResponse[size];
        }
    };

    public List<CastCredit> getCast() {
        return cast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CrewCredits> getCrew() {
        return crew;
    }

}