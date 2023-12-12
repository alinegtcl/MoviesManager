package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.luisitolentino.moviesmanager.databinding.FragmentListMoviesBinding
import com.luisitolentino.moviesmanager.domain.model.Movie
import com.luisitolentino.moviesmanager.presentation.view.adpter.MovieAdapter
import com.luisitolentino.moviesmanager.presentation.viewmodel.MovieState
import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMoviesFragment : Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesManagerViewModel by viewModel()

    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMovies()
        setupViewmodel()
    }

    private fun setupViewmodel() {
        lifecycleScope.launch {
            viewModel.stateList.collect {
                when (it) {
                    MovieState.EmptyState -> binding.textEmptyState.visibility = View.VISIBLE
                    is MovieState.Failure -> {}
                    MovieState.HideLoading -> binding.loadingListMovie.visibility = View.GONE
                    MovieState.ShowLoading -> binding.loadingListMovie.visibility = View.VISIBLE
                    is MovieState.SearchAllSuccess -> setupRecycler(it.movies)
                }
            }
        }
    }

    private fun setupRecycler(movies: List<Movie>) {
        movieAdapter = MovieAdapter(::onMovieClick, ::onMenuItemClick).apply { submitList(movies) }
        binding.recyclerListMovies.adapter = movieAdapter
    }

    private fun onMovieClick(movie: Movie) {
        Toast.makeText(activity, "clicou no filme ${movie.name}", Toast.LENGTH_SHORT).show()
    }

    private fun onMenuItemClick(movie: Movie) {
        Toast.makeText(activity, "clicou menu do ${movie.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}