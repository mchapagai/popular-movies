package com.mchapagai.core.service

import com.mchapagai.core.response.people.CombinedPersonResponse
import com.mchapagai.core.response.people.PersonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PeopleService {
    @GET("person/{personId}")
    fun getPersonDetailsById(
        @Path("personId") personId: Int
    ): Single<PersonResponse>

    @GET("person/{personId}/combined_credits")
    fun getPersonCombinedDetailsById(
        @Path("personId") personId: Int
    ): Observable<CombinedPersonResponse>

}