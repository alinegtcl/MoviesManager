package com.luisitolentino.moviesmanager.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luisitolentino.moviesmanager.data.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movieentity ORDER BY name")
    suspend fun getAllMoviesByName(): List<MovieEntity>?
    @Query("SELECT * FROM movieentity ORDER BY score DESC")
    suspend fun getAllMoviesByScore(): List<MovieEntity>?

    @Update
    suspend fun update(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)
}
