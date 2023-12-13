package com.luisitolentino.moviesmanager.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value=["name"], unique = true)])
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String,
    val releaseYear: String,
    val studio: String,
    val duration: Int,
    val flagMovieWatched: Boolean,
    val movieGenre: String,
    val score: Int? = -1
)
