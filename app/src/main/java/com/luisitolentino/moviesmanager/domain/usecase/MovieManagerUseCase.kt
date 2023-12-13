package com.luisitolentino.moviesmanager.domain.usecase

import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.utils.MMResult

interface MovieManagerUseCase {
    suspend fun insert(movie: Movie): MMResult<Unit, String>

    suspend fun getAllMoviesByName(): MMResult<List<Movie>, String>

}