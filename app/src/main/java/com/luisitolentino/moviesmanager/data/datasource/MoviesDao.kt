package com.luisitolentino.moviesmanager.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luisitolentino.moviesmanager.data.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movieentity ORDER BY name")
    suspend fun getAllMoviesByName(): List<MovieEntity>?
}
