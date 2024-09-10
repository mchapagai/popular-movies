package com.mchapagai.core.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mchapagai.core.api.PeopleAPI
import com.mchapagai.core.api.PeopleAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.response.people.CombinedPersonResponse
import com.mchapagai.core.response.people.PersonResponse
import com.mchapagai.core.service.PeopleService
import io.reactivex.disposables.CompositeDisposable

class PeopleViewModel : ViewModel() {

    private val peopleAPI: PeopleAPI = PeopleAPIImpl(
        RetrofitClient.createService(PeopleService::class.java)
    )

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val _person = mutableStateOf<PersonResponse?>(null)
    val personDetails: MutableState<PersonResponse?> get() = _person

    var combinedPersonDetails by mutableStateOf<CombinedPersonResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun fetchPersonDetails(personId: Int) {
        isLoading = true
        compositeDisposable.add(
            peopleAPI.getPersonDetailsById(personId)
                .subscribe(
                    { response ->
                        _person.value = response
                        isLoading = false
                    },
                    { error ->
                        // Handle error
                        _person.value = null
                        isLoading = false
                    }
                )
        )
    }

    fun fetchPersonCombinedDetails(personId: Int) {
        isLoading = true
        compositeDisposable.add(
            peopleAPI.getPersonCombinedDetailsById(personId)
                .subscribe(
                    { response ->
                        combinedPersonDetails = response
                        isLoading = false
                    },
                    { error ->
                        // Handle error
                        combinedPersonDetails = null
                        isLoading = false
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}