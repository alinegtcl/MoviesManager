package com.luisitolentino.moviesmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.model.MovieGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesManagerViewModel : ViewModel() {

    private val _stateList = MutableStateFlow<MovieState>(MovieState.HideLoading)
    val stateList = _stateList.asStateFlow()

    fun getAllMovies() {
        viewModelScope.launch {
            _stateList.value = MovieState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            _stateList.value = MovieState.HideLoading
            _stateList.value = MovieState.SearchAllSuccess(
                listOf(Movie("Sherlock", "2020", "warner", 120, true, MovieGenre(1L, "comedia"), 9))
            )
            //_stateList.value = MovieState.EmptyState
        }
    }
}