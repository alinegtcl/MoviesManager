package com.luisitolentino.moviesmanager.framework.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisitolentino.moviesmanager.data.datasource.MoviesDao
import com.luisitolentino.moviesmanager.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class MoviesManagerDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        const val MOVIES_MANAGER_DATABASE_NAME = "moviesmanager.db"
    }
}