package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.CombinedCastCredit;
import com.mchapagai.movies.model.CombinedCrewCredits;

import java.util.List;

public class CreditResponseCombined implements Parcelable {

    @SerializedName("cast")
    private List<CombinedCastCredit> cast;

    @SerializedName("id")
    private int id;

    @SerializedName("crew")
    private List<CombinedCrewCredits> crew;

    protected CreditResponseCombined(Parcel in) {
        id = in.readInt();
        crew = in.createTypedArrayList(CombinedCrewCredits.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(crew);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreditResponseCombined> CREATOR = new Creator<CreditResponseCombined>() {
        @Override
        public CreditResponseCombined createFromParcel(Parcel in) {
            return new CreditResponseCombined(in);
        }

        @Override
        public CreditResponseCombined[] newArray(int size) {
            return new CreditResponseCombined[size];
        }
    };

    public List<CombinedCastCredit> getCast() {
        return cast;
    }

    public int getId() {
        return id;
    }

    public List<CombinedCrewCredits> getCrew() {
        return crew;
    }
}