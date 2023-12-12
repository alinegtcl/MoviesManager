package com.luisitolentino.moviesmanager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val releaseYear: String,
    val studio: String,
    val duration: Int,
    val flagMovieWatched: Boolean,
    val movieGenre: MovieGenre,
    val score: Int? = -1,
    var id: Long = 0L
) : Parcelable