package com.luisitolentino.moviesmanager.data.repository

import com.luisitolentino.moviesmanager.data.datasource.MoviesDao
import com.luisitolentino.moviesmanager.data.entity.MovieEntity
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.usecase.MovieManagerUseCase
import com.luisitolentino.moviesmanager.domain.utils.MMResult

class MovieManagerRepositoryImpl(private val moviesDao: MoviesDao) : MovieManagerUseCase {
    override suspend fun insert(movie: Movie): MMResult<Unit, String> {
        return try {
            moviesDao.insert(toMovieEntity(movie))
            MMResult.Success(Unit)
        } catch (exception: Exception) {
            MMResult.Error(exception.message.toString())
        }
    }

    override suspend fun getAllMoviesByName(orderByName: Boolean): MMResult<List<Movie>, String> {
        val response = if(orderByName) moviesDao.getAllMoviesByName() else moviesDao.getAllMoviesByScore()
        return if (response != null)
            MMResult.Success(toDomain(response))
        else MMResult.Error("Você não tem livros cadastrados")
    }

    private fun toDomain(movies: List<MovieEntity>): List<Movie> {
        return movies.map {
            Movie(
                it.name,
                it.releaseYear,
                it.studio,
                it.duration,
                it.flagMovieWatched,
                it.movieGenre,
                it.score,
                it.id
            )
        }
    }

    override suspend fun update(movie: Movie): MMResult<Unit, String> {
        return try {
            moviesDao.update(toMovieEntity(movie))
            MMResult.Success(Unit)
        } catch (exception: Exception) {
            MMResult.Error(exception.message.toString())
        }
    }

    override suspend fun delete(movie: Movie): MMResult<Unit, String> {
        return try {
            moviesDao.delete(toMovieEntity(movie))
            MMResult.Success(Unit)
        } catch (exception: Exception) {
            MMResult.Error(exception.message.toString())
        }
    }

    private fun toMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            movie.id,
            movie.name,
            movie.releaseYear,
            movie.studio,
            movie.duration,
            movie.flagMovieWatched,
            movie.movieGenre,
            movie.score
        )
    }
}