package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.databinding.FragmentMovieDetailBinding
import com.example.mad03_fragments_and_navigation.models.MovieStore
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())   // get navigation arguments

        when(val movieEntry = MovieStore().findMovieByUUID(args.movieId)){
            null -> {
                Toast.makeText(requireContext(), "Could not load movie data", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            else -> binding.movie = movieEntry
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

        binding.addToFavorites.setOnClickListener {
            Toast.makeText(requireContext(), "Movie saved to favorites.", Toast.LENGTH_SHORT).show()
            movieFavoriteViewModel.addMovie(binding.movie!!)
        }

        return binding.root
    }
}