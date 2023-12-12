package com.luisitolentino.moviesmanager.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenre(val id: Long, val description: String) : Parcelable