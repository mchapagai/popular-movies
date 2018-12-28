package com.mchapagai.movies.model.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.CombinedCastCredit;
import com.mchapagai.movies.model.CombinedCrewCredits;
import java.util.List;

public class CombinedPersonResponse implements Parcelable {

    @SerializedName("cast")
    private List<CombinedCastCredit> cast;

    @SerializedName("id")
    private int id;

    @SerializedName("crew")
    private List<CombinedCrewCredits> crew;

    protected CombinedPersonResponse(Parcel in) {
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

    public static final Creator<CombinedPersonResponse> CREATOR = new Creator<CombinedPersonResponse>() {
        @Override
        public CombinedPersonResponse createFromParcel(Parcel in) {
            return new CombinedPersonResponse(in);
        }

        @Override
        public CombinedPersonResponse[] newArray(int size) {
            return new CombinedPersonResponse[size];
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