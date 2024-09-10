package com.mchapagai.core.response.people

import com.google.gson.annotations.SerializedName
import com.mchapagai.core.utils.DateTimeUtils

data class PersonResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("also_known_as") val alsoKnownAs: List<String>,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("biography") val biography: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val personId: Int,
    @SerializedName("homepage") val homepage: String
) {
    fun personName(): String {
        return "$name \"$knownForDepartment\""
    }

    fun birthDayAndPlace(): String {
        return "${DateTimeUtils.getNameOfMonth(birthday)} | $placeOfBirth"
    }

    val formattedBirthday: String?
        get() = if (!birthday.isNullOrEmpty()) {
            DateTimeUtils.getYearOnly(birthday)
        } else {
            null
        }
}