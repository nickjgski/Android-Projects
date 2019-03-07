package com.nickjgski.moviedb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MovieRepository(private val movieDao: MovieDao) {

    val allMoviesByTitle: LiveData<List<Movie>> = movieDao.getAllMoviesByTitle()
    val allMoviesByRating: LiveData<List<Movie>> = movieDao.getAllMoviesByRating()

    @WorkerThread
    suspend fun insert(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    @WorkerThread
    suspend fun deleteAll() {
        movieDao.deleteAll()
    }

}