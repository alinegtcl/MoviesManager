package com.luisitolentino.moviesmanager.domain.model

data class Movie(
    val id: Long,
    val name: String,
    val releaseYear: String,
    val studio: String,
    val duration: Int,
    val flagMovieWatched: Boolean,
    val score: Int? = -1,
    val movieGenre: MovieGenre
)