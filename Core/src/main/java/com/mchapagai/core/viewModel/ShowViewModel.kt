package com.mchapagai.core.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mchapagai.core.api.ShowAPI
import com.mchapagai.core.api.ShowAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.response.shows.ShowResponse
import com.mchapagai.core.service.ShowService
import io.reactivex.disposables.CompositeDisposable

class ShowViewModel : ViewModel() {
    private val showAPI: ShowAPI by lazy {
        ShowAPIImpl(RetrofitClient.createService(ShowService::class.java))
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _shows = mutableStateListOf<ShowResponse>()
    val shows: List<ShowResponse> get() = _shows
    private var currentPage = 1
    private var isLoading = false

    init {
        fetchShows()
    }

    fun fetchShows() {
        if (isLoading) return
        isLoading = true
        showAPI.discoverShows(currentPage, "popularity.desc")
            .subscribe({ response ->
                _shows.addAll(response.shows)
                currentPage++
                isLoading = false
            }, { error ->
                isLoading = false
            }).let(compositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}