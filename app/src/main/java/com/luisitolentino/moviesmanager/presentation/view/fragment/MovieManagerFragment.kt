package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddMovie()
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
                    MovieGenre(1L, "comedia"),
                    editTextMovieScore.text.toString().toInt()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}