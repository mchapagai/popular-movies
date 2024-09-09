package com.mchapagai.core.response.movies

import com.google.gson.annotations.SerializedName

data class MovieCrewResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("gender") var gender: Int? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("known_for_department") var knownForDepartment: String? = null,
    // val knownForDepartment: Department,
    @SerializedName("name") var name: String = "",
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("profile_path") var profilePath: String? = null,
    @SerializedName("credit_id") var creditId: String? = null,
    @SerializedName("department") var department: String? = null,
    @SerializedName("job") var job: String = ""
)

enum class Department(val value: String) {
    Acting("Acting"),
    Art("Art"),
    Camera("Camera"),
    CostumeMakeUp("Costume & Make-Up"),
    Crew("Crew"),
    Directing("Directing"),
    Editing("Editing"),
    Production("Production"),
    Sound("Sound"),
    VisualEffects("Visual Effects"),
    Writing("Writing");

    companion object {
        public fun fromValue(value: String): Department = when (value) {
            "Acting"            -> Acting
            "Art"               -> Art
            "Camera"            -> Camera
            "Costume & Make-Up" -> CostumeMakeUp
            "Crew"              -> Crew
            "Directing"         -> Directing
            "Editing"           -> Editing
            "Production"        -> Production
            "Sound"             -> Sound
            "Visual Effects"    -> VisualEffects
            "Writing"           -> Writing
            else                -> throw IllegalArgumentException()
        }
    }
}