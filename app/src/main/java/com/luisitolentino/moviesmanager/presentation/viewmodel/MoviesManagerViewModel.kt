package com.luisitolentino.moviesmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.usecase.MovieManagerUseCase
import com.luisitolentino.moviesmanager.domain.utils.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesManagerViewModel(private val useCase: MovieManagerUseCase) : ViewModel() {

    private val _stateList = MutableStateFlow<MovieState>(MovieState.HideLoading)
    val stateList = _stateList.asStateFlow()

    private val _stateManagement =
        MutableStateFlow<MovieManagerState>(MovieManagerState.HideLoading)
    val stateManagement = _stateManagement.asStateFlow()

    fun getAllMovies() {
        viewModelScope.launch {
            _stateList.value = MovieState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            _stateList.value = MovieState.HideLoading
            _stateList.value = MovieState.SearchAllSuccess(
                listOf(Movie("Sherlock", "2020", "warner", 120, true, "Comedia", 9))
            )
            //_stateList.value = MovieState.EmptyState
        }
    }

    fun insert(movie: Movie) {
        viewModelScope.launch {
            _stateManagement.value = MovieManagerState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            val response = useCase.insert(movie)
            _stateManagement.value = MovieManagerState.HideLoading
            response.flow(
                {
                    _stateManagement.value = MovieManagerState.InsertSuccess
                }, {
                    _stateManagement.value = MovieManagerState.Failure(it)
                }
            )

        }
    }
}
