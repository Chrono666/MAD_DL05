package com.example.mad03_fragments_and_navigation.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mad03_fragments_and_navigation.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMovie(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun clear()

    @Query("SELECT * FROM movie_table ORDER BY id DESC LIMIT 1")
    fun getMovie(): Movie

    @Query("SELECT * FROM movie_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Movie>>
}