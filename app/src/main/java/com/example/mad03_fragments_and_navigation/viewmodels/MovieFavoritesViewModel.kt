package com.example.mad03_fragments_and_navigation.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.models.Question
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import kotlinx.coroutines.launch

class MovieFavoritesViewModel(
    private val repository: MovieRepository,
    application: Application
) : ViewModel() {

//    private var movie = MutableLiveData<Movie?>()

    private val _movies = MutableLiveData<LiveData<List<Movie>>>()
    val movies: LiveData<LiveData<List<Movie>>>
        get() = _movies

    init {
        _movies.value = repository.getAllMovies()
    }


/*
    private var readAll: LiveData<List<Movie>>

    init {
        readAll = repository.getAllMovies()
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createMovie(movie)
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun clearMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearMovies()
        }
    }
*/

}