package com.mchapagai.movies.model.movies.binding;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.mchapagai.movies.model.movies.CombinedCastCredit;
import com.mchapagai.movies.model.movies.CombinedCrewCredits;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CombinedPersonResponse that = (CombinedPersonResponse) o;
        return id == that.id &&
                Objects.equals(cast, that.cast) &&
                Objects.equals(crew, that.crew);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cast, id, crew);
    }
}