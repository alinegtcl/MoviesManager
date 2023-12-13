package com.luisitolentino.moviesmanager.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.luisitolentino.moviesmanager.data.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(movie: MovieEntity)
}
