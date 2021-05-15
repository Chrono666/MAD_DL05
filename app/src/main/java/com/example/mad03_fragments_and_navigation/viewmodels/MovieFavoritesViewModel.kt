package com.example.mad03_fragments_and_navigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieFavoritesViewModel(
    private val repository: MovieRepository,
) : ViewModel() {


    val movies: LiveData<List<Movie>> = repository.getAllMovies()

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

}