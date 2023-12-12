package com.luisitolentino.moviesmanager.presentation.viewmodel

import com.luisitolentino.moviesmanager.domain.model.Movie

sealed class MovieState {
    data class SearchAllSuccess(val movies: List<Movie>) : MovieState()
    data class Failure(val errorMessage: String) : MovieState()
    data object EmptyState : MovieState()
    data object ShowLoading : MovieState()
    data object HideLoading : MovieState()
}
