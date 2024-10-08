package com.mchapagai.core.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mchapagai.core.api.MovieAPI
import com.mchapagai.core.api.MoviesAPIImpl
import com.mchapagai.core.common.RetrofitClient
import com.mchapagai.core.model.MovieCombinedCreditModel
import com.mchapagai.core.response.common.ReviewListResponse
import com.mchapagai.core.response.common.VideoListResponse
import com.mchapagai.core.response.movies.MovieDetailsResponse
import com.mchapagai.core.response.movies.MovieResponse
import com.mchapagai.core.service.MovieService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieAPI: MovieAPI = MoviesAPIImpl(
        RetrofitClient.createService(MovieService::class.java)
    )

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val _movies = mutableStateListOf<MovieResponse>()
    val movies: List<MovieResponse> get() = _movies

    private val _movieDetails = mutableStateOf<MovieDetailsResponse?>(null)
    val movieDetails: MutableState<MovieDetailsResponse?> get() = _movieDetails

    var combinedCredits by mutableStateOf<List<MovieCombinedCreditModel>>(emptyList())
        private set

    var videoList by mutableStateOf<VideoListResponse?>(null)
        private set
    var reviewList by mutableStateOf<ReviewListResponse?>(null)
        private set

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


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

    fun fetchMovieDetails(movieId: Int) {
//        _isLoading.value = true
        compositeDisposable.add(
            movieAPI.fetchMovieDetailsByMovieId(movieId)
                .subscribe(
                    { response ->
                        _movieDetails.value = response
//                        _isLoading.value = false
                    },
                    { error ->
                        _error.value = error
//                        _isLoading.value = false
                    })
        )
    }

    fun fetchMovieCreditDetailsByCreditId(movieId: Int) {
        isLoading = true
        compositeDisposable.add(
            movieAPI.getMovieCreditDetailsByCreditId(movieId)
                .map { response ->
                    MovieCombinedCreditModel(
                        0, "", "", ""
                    ).combinedCreditsResponse(
                        response.cast, response.crew
                    )
                }
                .subscribe(
                    { combinedCredit ->
                        combinedCredits = combinedCredit
                        isLoading = false
                    },
                    { error ->
                        combinedCredits = emptyList()
                        _error.value = error
                    }
                )
        )
    }

    fun fetchMovieVideos(movieId: Int) {
        isLoading = true
        compositeDisposable.add(
            movieAPI.getMovieVideosById(movieId)
                .subscribe(
                    { response ->
                        videoList = response
                        isLoading = false
                    },
                    { error ->
                        // Handle error
                        videoList = null
                        isLoading = false
                    }
                )
        )
    }

    fun fetchMovieReviews(movieId: Int) {
        isLoading = true
        compositeDisposable.add(
            movieAPI.getMovieReviewsById(movieId)
                .take(3)
                .subscribe(
                    { response ->
                        reviewList = response
                        isLoading = false
                    },
                    { error ->
                        // Handle error
                        reviewList = null
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