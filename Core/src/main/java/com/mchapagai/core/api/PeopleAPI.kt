package com.mchapagai.core.api

import com.mchapagai.core.response.people.CombinedPersonResponse
import com.mchapagai.core.response.people.PersonResponse
import io.reactivex.Observable
import io.reactivex.Single

interface PeopleAPI {
    fun getPersonDetailsById(personId: Int
    ): Single<PersonResponse>

    fun getPersonCombinedDetailsById(personId: Int
    ): Observable<CombinedPersonResponse>
}