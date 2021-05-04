package com.example.mad03_fragments_and_navigation.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mad03_fragments_and_navigation.models.Movie

@Dao
interface MovieDao {

    @Insert
    fun createMovie(movie: Movie): Long

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movieId: Long)

    @Query("DELETE FROM movie_table")
    fun clear()

    @Query("SELECT * FROM movie_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Movie>>
}