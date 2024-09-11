package com.mchapagai.core.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mchapagai.core.api.SearchAPI
import com.mchapagai.core.api.SearchAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.response.search.SearchResultResponse
import com.mchapagai.core.service.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private val searchAPI: SearchAPI = SearchAPIImpl(
        RetrofitClient.createService(SearchService::class.java)
    )

    private val disposables = CompositeDisposable()
    private val searchResults = mutableStateListOf<SearchResultResponse>()
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    fun executeSearch(query: String) {
        if (query.isBlank()) {
            _searchState.value = SearchState.Idle
            return
        }

        _searchState.value = SearchState.Loading
        disposables.add(
            searchAPI.search(query)
                .map { response ->
                    response.results.filter { result ->
                        result.title?.contains(query, ignoreCase = true) == true
                    }
                }
                .distinctUntilChanged()
                .subscribe(
                    { results ->
                        searchResults.addAll(results)
                        _searchState.value = SearchState.Success(searchResults)
                    },
                    { error ->
                        _searchState.value = SearchState.Error(error)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class SearchState {
        data object Idle : SearchState()
        data object Loading : SearchState()
        data class Success(val results: List<SearchResultResponse>) : SearchState()
        data class Error(val throwable: Throwable) : SearchState()
    }
}