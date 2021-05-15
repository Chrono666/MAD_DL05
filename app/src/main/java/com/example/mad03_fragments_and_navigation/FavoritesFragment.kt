package com.example.mad03_fragments_and_navigation

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    private lateinit var movieFavoriteViewModel: MovieFavoritesViewModel
    private lateinit var viewModelFactory: MovieFavoritesViewModelFactory
    private var note = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        val adapter = FavoritesListAdapter(
            dataSet = listOf(),     // start with empty list
            onDeleteClicked = { movie -> onDeleteMovieClicked(movie) },   // pass functions to adapter
            onEditClicked = { movie -> onEditMovieClicked(movie) },           // pass functions to adapter
        )

        with(binding) {
            recyclerView.adapter = adapter
        }

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getDatabase(application).movieDao
        val repository = MovieRepository.getInstance(dataSource)
        viewModelFactory = MovieFavoritesViewModelFactory(repository)

        movieFavoriteViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MovieFavoritesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.movieTrackerViewModel = movieFavoriteViewModel

        movieFavoriteViewModel.movies.observe(
            viewLifecycleOwner,
            Observer { movies -> adapter.updateDataSet(movies) })

        binding.clearBtn.setOnClickListener {
            movieFavoriteViewModel.clearMovies()
        }

        return binding.root
    }

    // This is called when recyclerview item edit button is clicked
    private fun onEditMovieClicked(movieObj: Movie) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Title")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton(
            "Save"
        ) { dialog, which ->
            note = input.text.toString()
            movieObj.note = note
            movieFavoriteViewModel.updateMovie(movieObj)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }

    // This is called when recyclerview item remove button is clicked
    private fun onDeleteMovieClicked(movie: Movie) {
        movieFavoriteViewModel.deleteMovie(movie)
    }

}