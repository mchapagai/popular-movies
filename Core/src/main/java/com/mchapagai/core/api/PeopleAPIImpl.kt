package com.mchapagai.core.api

import com.mchapagai.core.response.people.CombinedPersonResponse
import com.mchapagai.core.response.people.PersonResponse
import com.mchapagai.core.service.PeopleService
import com.mchapagai.core.utils.RxUtils
import io.reactivex.Observable
import io.reactivex.Single

class PeopleAPIImpl(private val service: PeopleService) : PeopleAPI {
    override fun getPersonDetailsById(personId: Int): Single<PersonResponse> {
        return service.getPersonDetailsById(personId)
            .compose(RxUtils.applySingleSchedulers())
    }

    override fun getPersonCombinedDetailsById(personId: Int): Observable<CombinedPersonResponse> {
        return service.getPersonCombinedDetailsById(personId)
            .compose(RxUtils.applyObservableSchedulers())
    }
}