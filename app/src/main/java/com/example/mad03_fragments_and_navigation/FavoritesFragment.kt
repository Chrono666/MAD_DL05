package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mad03_fragments_and_navigation.adapters.FavoritesListAdapter
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.databinding.FragmentFavoritesBinding
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        val adapter = FavoritesListAdapter(
            dataSet = listOf(),     // start with empty list
            onDeleteClicked = { movieId -> onDeleteMovieClicked(movieId) },   // pass functions to adapter
            onEditClicked = { movie -> onEditMovieClicked(movie) },           // pass functions to adapter
        )

        with(binding) {
            recyclerView.adapter = adapter
        }

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getDatabase(application).movieDao
        val repository = MovieRepository.getInstance(dataSource)
        val viewModelFactory = MovieFavoritesViewModelFactory(repository)

        val movieFavoriteViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MovieFavoritesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.movieTrackerViewModel = movieFavoriteViewModel

        return binding.root
    }

    // This is called when recyclerview item edit button is clicked
    private fun onEditMovieClicked(movieObj: Movie) {
        // TODO implement me
    }

    // This is called when recyclerview item remove button is clicked
    private fun onDeleteMovieClicked(movieId: Long) {
        // TODO implement me
    }
}