package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.luisitolentino.moviesmanager.R
import com.luisitolentino.moviesmanager.databinding.FragmentMovieManagerBinding
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.domain.utils.Constants.EMPTY_STRING
import com.luisitolentino.moviesmanager.presentation.viewmodel.MovieManagerState
import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MovieManagerFragment : Fragment() {

    private val viewModel: MoviesManagerViewModel by viewModel()

    private var _binding: FragmentMovieManagerBinding? = null
    private val binding get() = _binding!!

    private val args: MovieManagerFragmentArgs by navArgs()

    private lateinit var movie: Movie

    private var isEdit by Delegates.notNull<Boolean>()

    private val movieGenreList = listOf(
        "Comédia",
        "Drama",
        "Terror",
        "Romance",
        "Aventura"
    )

    private var name = EMPTY_STRING
    private var genreSelected = EMPTY_STRING
    private var releaseYear = EMPTY_STRING
    private var studio = EMPTY_STRING
    private var duration = 0
    private var watched = false
    private var score = -1

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
        setupInputListeners()
        if (args.movie == null) {
            setupAddView()
        } else {
            setupEditView(args.movie!!)
        }
        setupSaveMovieButton()
        setupViewModel()
    }

    private fun setupAddView() {
        isEdit = false
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Adicionar filme"
        binding.apply {
            editTextMovieName.setText(name)
            editTextMovieName.isEnabled = true
            editTextReleaseYear.setText(releaseYear)
            editTextMovieStudio.setText(studio)
            editTextMovieDuration.setText(duration.toString())
            checkMovieWatched.isChecked = false
            if (score < 0) editTextMovieScore.setText(EMPTY_STRING)
            else editTextMovieScore.setText(score.toString())
            spinnerMovieGenre.setSelection(0)
        }
    }

    private fun setupEditView(movie: Movie) {
        isEdit = true
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Alterar filme"
        binding.apply {
            editTextMovieName.setText(movie.name)
            editTextMovieName.isEnabled = false
            editTextReleaseYear.setText(movie.releaseYear)
            editTextMovieStudio.setText(movie.studio)
            editTextMovieDuration.setText(movie.duration.toString())
            checkMovieWatched.isChecked = movie.flagMovieWatched
            if (movie.score!! < 0) editTextMovieScore.setText(EMPTY_STRING)
            else editTextMovieScore.setText(movie.score.toString())
            spinnerMovieGenre.setSelection(movieGenreList.indexOf(movie.movieGenre))
        }
    }

    private fun setupSpinnerMovieGenre() {
        val adapter = ArrayAdapter(
            requireContext(),
            org.koin.android.R.layout.support_simple_spinner_dropdown_item,
            movieGenreList
        )
        binding.spinnerMovieGenre.adapter = adapter
        binding.spinnerMovieGenre.itemSelected { genre ->
            movieGenreList.forEach {
                if (it == genre)
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
                    name,
                    releaseYear,
                    studio,
                    duration,
                    watched,
                    genreSelected,
                    score,
                    args.movie?.id ?: 0L
                )
            }
            if (isEdit) {
                viewModel.update(movie)
            } else {
                viewModel.insert(movie)
            }
        }
    }

    private fun setupInputListeners() {
        binding.editTextMovieName.addTextChangedListener {
            if (binding.editTextMovieName.text.toString() != EMPTY_STRING) {
                name = binding.editTextMovieName.text.toString()
            }
            validateToggleButton()
        }
        binding.editTextReleaseYear.addTextChangedListener {
            releaseYear = binding.editTextReleaseYear.text.toString()
            validateToggleButton()
        }
        binding.editTextMovieStudio.addTextChangedListener {
            studio = binding.editTextMovieStudio.text.toString()
            validateToggleButton()
        }
        binding.editTextMovieDuration.addTextChangedListener {
            try {
                duration = binding.editTextMovieDuration.text.toString().toInt()
            } catch (e: Exception) {

            }
            validateToggleButton()
        }
        binding.checkMovieWatched.setOnCheckedChangeListener { _, isChecked ->
            watched = isChecked
            if (watched) {
                binding.editTextMovieScore.visibility = View.VISIBLE
            } else {
                score = -1
                binding.editTextMovieScore.visibility = View.GONE
            }
            validateToggleButton()

        }
        binding.editTextMovieScore.addTextChangedListener {
            try {
                score = binding.editTextMovieScore.text.toString().toInt()
                if (score !in 0..10) {
                }
            } catch (e: Exception) {
                score = -1
            }
            validateToggleButton()
        }
    }

    private fun validateToggleButton() {
        val isValid =
            binding.editTextMovieName.text.toString() != EMPTY_STRING && (!binding.checkMovieWatched.isChecked || (binding.checkMovieWatched.isChecked && score in 0..10))
        binding.buttonSaveMovie.isEnabled = isValid

    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.stateManagement.collect { state ->
                when (state) {
                    is MovieManagerState.Failure -> {
                        Toast.makeText(
                            requireContext(),
                            "$state.errorMessage", Toast.LENGTH_SHORT
                        ).show()
                    }

                    MovieManagerState.HideLoading -> binding.loadingManagerMovie.visibility =
                        View.GONE

                    MovieManagerState.InsertSuccess -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.label_movie_added), Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    MovieManagerState.ShowLoading -> binding.loadingManagerMovie.visibility =
                        View.VISIBLE

                    MovieManagerState.UpdateSuccess -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.label_movie_changed), Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}