package com.mchapagai.core.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mchapagai.core.api.MovieAPI
import com.mchapagai.core.api.MoviesAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.response.movies.MovieResponse
import com.mchapagai.core.service.MovieService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieAPI: MovieAPI = MoviesAPIImpl(
        RetrofitClient.createService(MovieService::class.java)
    )

    private val compositeDisposable = CompositeDisposable()
    private val _movies = mutableStateListOf<MovieResponse>()
    val movies: List<MovieResponse> get() = _movies

    private var currentPage = 1
    private var isLoading = false

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            movieAPI.discoverMovies(currentPage, "popularity.desc")
                .subscribe({ response ->
                    Log.d("MovieViewModel", "Response received: $response") // Log the response
                    _movies.addAll(response.movies)
                    currentPage++
                    isLoading = false
                }, { error ->
                    Log.e("MovieViewModel", "Error fetching movies", error) // Log the error
                    isLoading = false
                }).let(compositeDisposable::add)
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}