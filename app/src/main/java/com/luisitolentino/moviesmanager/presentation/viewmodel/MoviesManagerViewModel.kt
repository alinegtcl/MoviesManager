package com.luisitolentino.moviesmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.usecase.MovieManagerUseCase
import com.luisitolentino.moviesmanager.domain.utils.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesManagerViewModel(private val useCase: MovieManagerUseCase) : ViewModel() {

    private val _stateList = MutableStateFlow<MovieState>(MovieState.HideLoading)
    val stateList = _stateList.asStateFlow()

    private val _stateManagement =
        MutableStateFlow<MovieManagerState>(MovieManagerState.HideLoading)
    val stateManagement = _stateManagement.asStateFlow()

    fun getAllMoviesByName() {
        viewModelScope.launch {
            _stateList.value = MovieState.ShowLoading
            val response = useCase.getAllMoviesByName()
            _stateList.value = MovieState.HideLoading
            response.flow(
                { movies ->
                    if (movies.isNotEmpty())
                        _stateList.value = MovieState.SearchAllSuccess(movies)
                    else
                        _stateList.value = MovieState.EmptyState
                },
                {
                    _stateList.value = MovieState.Failure(it)
                }
            )
        }
    }

    fun insert(movie: Movie) {
        viewModelScope.launch {
            _stateManagement.value = MovieManagerState.ShowLoading
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

    fun update(movie: Movie) {
        viewModelScope.launch {
            _stateManagement.value = MovieManagerState.ShowLoading
            val response = useCase.update(movie)
            _stateManagement.value = MovieManagerState.HideLoading
            response.flow(
                {
                    _stateManagement.value = MovieManagerState.UpdateSuccess
                }, {
                    _stateManagement.value = MovieManagerState.Failure(it)
                }
            )
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            _stateList.value = MovieState.ShowLoading
            val response = useCase.delete(movie)
            _stateList.value = MovieState.HideLoading
            response.flow(
                {
                    _stateList.value = MovieState.DeleteSuccess

                },
                {
                    _stateList.value = MovieState.Failure(it)
                }
            )
        }
    }
}
