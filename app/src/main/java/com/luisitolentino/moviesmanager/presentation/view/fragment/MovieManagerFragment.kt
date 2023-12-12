package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.luisitolentino.moviesmanager.databinding.FragmentMovieManagerBinding
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.model.MovieGenre
import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MovieManagerFragment : Fragment() {

    private val viewModel: MoviesManagerViewModel by viewModel()

    private var _binding: FragmentMovieManagerBinding? = null
    private val binding get() = _binding!!

    private val args: MovieManagerFragmentArgs by navArgs()

    private lateinit var movie: Movie

    private var isEdit by Delegates.notNull<Boolean>()

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
        if (args.movie == null) {
            setupAddView()
        } else {
            setupEditView(args.movie!!)
        }
        setupSaveMovieButton()
    }

    private fun setupAddView() {
        isEdit = false
    }

    private fun setupEditView(movie: Movie) {
        isEdit = true
        binding.apply {
            editTextMovieName.setText(movie.name)
            editTextReleaseYear.setText(movie.releaseYear)
            editTextMovieStudio.setText(movie.studio)
            editTextMovieDuration.setText(movie.duration.toString())
            checkMovieWatched.isChecked = movie.flagMovieWatched
            editTextMovieScore.setText(movie.score.toString())
            spinnerMovieGenre.setSelection(movieGenreList.indexOf(movie.movieGenre))
        }
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

    private fun setupSaveMovieButton() {
        binding.buttonSaveMovie.setOnClickListener {
            binding.apply {
                movie = Movie(
                    editTextMovieName.text.toString(),
                    editTextReleaseYear.text.toString(),
                    editTextMovieStudio.text.toString(),
                    editTextMovieDuration.text.toString().toInt(),
                    checkMovieWatched.isChecked,
                    genreSelected!!,
                    editTextMovieScore.text.toString().toInt(),
                    args.movie?.id ?: 0L
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}