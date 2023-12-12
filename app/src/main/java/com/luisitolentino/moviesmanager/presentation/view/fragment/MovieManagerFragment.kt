package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.luisitolentino.moviesmanager.databinding.FragmentMovieManagerBinding
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.model.MovieGenre
import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieManagerFragment : Fragment() {

    private val viewModel: MoviesManagerViewModel by viewModel()

    private var _binding: FragmentMovieManagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private val movieGenreList = listOf<MovieGenre>(
        MovieGenre(1L, "Comédia"),
        MovieGenre(2L, "Drama"),
        MovieGenre(3L, "Terror")
    )
    private var genreSelected: MovieGenre? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinnerMovieGenre()
        setupAddMovie()
    }

    private fun setupSpinnerMovieGenre() {
        val adapter = ArrayAdapter(
            requireContext(),
            org.koin.android.R.layout.support_simple_spinner_dropdown_item,
            movieGenreList.map { it.description }
        )
        binding.spinnerMovieGenre.adapter = adapter
        binding.spinnerMovieGenre.itemSelected { genre ->
            movieGenreList.forEach {
                if (it.description == genre)
                    genreSelected = it
            }
        }
    }

    private fun Spinner.itemSelected(action: (genre: String) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                action(parent?.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun setupAddMovie() {
        binding.buttonAddMovie.setOnClickListener {
            binding.apply {
                movie = Movie(
                    editTextMovieName.text.toString(),
                    editTextReleaseYear.text.toString(),
                    editTextMovieStudio.text.toString(),
                    editTextMovieDuration.text.toString().toInt(),
                    checkMovieWatched.isChecked,
                    genreSelected!!,
                    editTextMovieScore.text.toString().toInt()
                )
            }
            Toast.makeText(requireContext(), "$genreSelected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}