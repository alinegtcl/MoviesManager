package com.luisitolentino.moviesmanager.presentation.viewmodel

sealed class MovieManagerState {
    data object InsertSuccess : MovieManagerState()
    data object UpdateSuccess : MovieManagerState()
    data object ShowLoading : MovieManagerState()
    data object HideLoading : MovieManagerState()
    data class Failure(val errorMessage: String) : MovieManagerState()
}