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

    val list = listOf(
        Movie(
            1L,
            "Young Sheldon",
            "2023",
            "Warner",
            100,
            true,
            9,
            MovieGenre(1L, "Comédia")
        ),
        Movie(
            2L,
            "Sherlock Holmes",
            "2019",
            "Warner",
            115,
            false,
            movieGenre = MovieGenre(2L, "Drama")
        )
    )

    fun getAllMovies() {
        viewModelScope.launch {
            _stateList.value = MovieState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            _stateList.value = MovieState.HideLoading
            _stateList.value = MovieState.SearchAllSuccess(list)
            // _stateList.value = MovieState.EmptyState
        }
    }
}
