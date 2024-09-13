package com.mchapagai.core.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mchapagai.core.api.PeopleAPI
import com.mchapagai.core.api.PeopleAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.model.PeopleCombinedCreditModel
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

    private val _combinedPersonDetails =
        mutableStateOf<List<PeopleCombinedCreditModel>>(emptyList())
    val combinedPersonDetails: MutableState<List<PeopleCombinedCreditModel>>
        get() = _combinedPersonDetails

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> get() = _isLoading

    fun fetchPersonDetails(personId: Int) {
        _isLoading.value = true
        compositeDisposable.add(
            peopleAPI.getPersonDetailsById(personId)
                .subscribe(
                    { response ->
                        _person.value = response
                        _isLoading.value = false
                    },
                    { error ->
                        // Handle error
                        _person.value = null
                        _isLoading.value = false
                    }
                )
        )
    }

    fun fetchPersonCombinedDetails(personId: Int) {
        _isLoading.value = true
        compositeDisposable.add(
            peopleAPI.getPersonCombinedDetailsById(personId)
                .map { response ->
                    PeopleCombinedCreditModel(
                    ).personCombinedResponse(
                        response.cast.filter {
                            it.posterPath != null
                        }, response.crew.filter {
                            it.posterPath != null
                        }
                    )
                }
                .subscribe(
                    { credits ->
                        _combinedPersonDetails.value = credits
                        _isLoading.value = false
                    },
                    { error ->
                        _combinedPersonDetails.value = emptyList()
                        _isLoading.value = false
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}