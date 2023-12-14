package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.luisitolentino.moviesmanager.R
import com.luisitolentino.moviesmanager.databinding.FragmentDetailMovieBinding
import com.luisitolentino.moviesmanager.domain.utils.Constants.EMPTY_STRING

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null

    private val args: DetailMovieFragmentArgs by navArgs()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Detalhes do filme"

        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            textMovieName.text = args.selectedMovie.name
            textMovieGenre.text = args.selectedMovie.movieGenre
            textReleaseYear.text = if (args.selectedMovie.releaseYear == EMPTY_STRING)
                getString(R.string.label_uninformed) else args.selectedMovie.releaseYear
            textMovieStudio.text =
                if (args.selectedMovie.studio == EMPTY_STRING) getString(R.string.label_uninformed)
                else args.selectedMovie.studio
            if (args.selectedMovie.duration > 0)
                textMovieDuration.text =
                    getString(R.string.label_x_minutes, args.selectedMovie.duration.toString())
            else textMovieDuration.text = getString(R.string.label_uninformed)
            textMovieWatched.text =
                if (args.selectedMovie.flagMovieWatched) getString(R.string.label_movie_watched)
                else getString(R.string.label_movie_not_watched)
            val score = args.selectedMovie.score ?: -1
            textMovieScore.text =
                if (score < 0) getString(R.string.label_not_yet_rated) else getString(
                    R.string.label_detail_score,
                    args.selectedMovie.score.toString()
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}