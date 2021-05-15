package com.example.mad03_fragments_and_navigation.repositories

import androidx.lifecycle.LiveData
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.models.Movie

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun createMovie(movie: Movie) = movieDao.createMovie(movie)
    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)
    suspend fun clearMovies() = movieDao.clear()
    fun getMovie() = movieDao.getMovie()
    fun getAllMovies(): LiveData<List<Movie>> = movieDao.getAll()


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(dao: MovieDao) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(dao).also { instance = it }
            }
    }
}