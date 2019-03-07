package com.nickjgski.moviedb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table order BY  title ASC")
    fun getAllMoviesByTitle(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table order BY vote_average DESC")
    fun getAllMoviesByRating(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

}