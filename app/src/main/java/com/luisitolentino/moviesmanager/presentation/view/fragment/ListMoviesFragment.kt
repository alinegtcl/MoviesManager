package com.luisitolentino.moviesmanager.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.luisitolentino.moviesmanager.R
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

    private var positionDeleted: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMoviesByName()
        setupViewmodel()
        setupButtons()
    }

    private fun setupButtons() {
        binding.buttonAddNewMovie.setOnClickListener {
            findNavController().navigate(
                ListMoviesFragmentDirections.goToMovieManagerFragment()
            )
        }
        binding.buttonOrderByName.setOnClickListener {
            viewModel.getAllMoviesByName()
        }
        binding.buttonOrderByScore.setOnClickListener {
            viewModel.getAllMoviesByName(orderByName = false)
        }
    }

    private fun setupViewmodel() {
        lifecycleScope.launch {
            viewModel.stateList.collect { state ->
                when (state) {
                    MovieState.EmptyState -> {
                        binding.recyclerListMovies.visibility = View.GONE
                        binding.buttonOrderByName.visibility = View.GONE
                        binding.buttonOrderByScore.visibility = View.GONE
                        binding.textOrderByLabel.visibility = View.GONE
                        binding.textEmptyState.visibility = View.VISIBLE
                    }

                    is MovieState.Failure -> {
                        Toast.makeText(
                            requireContext(),
                            "$state.errorMessage", Toast.LENGTH_SHORT
                        ).show()
                    }

                    MovieState.HideLoading -> binding.loadingListMovie.visibility = View.GONE
                    MovieState.ShowLoading -> binding.loadingListMovie.visibility = View.VISIBLE
                    is MovieState.SearchAllSuccess -> {
                        binding.recyclerListMovies.visibility = View.VISIBLE
                        binding.buttonOrderByName.visibility = View.VISIBLE
                        binding.buttonOrderByScore.visibility = View.VISIBLE
                        binding.textOrderByLabel.visibility = View.VISIBLE
                        setupRecycler(state.movies)
                    }

                    MovieState.DeleteSuccess -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.label_movie_deleted), Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getAllMoviesByName()
                    }
                }
            }
        }
    }

    private fun setupRecycler(movies: List<Movie>) {
        movieAdapter = MovieAdapter(
            ::onMovieClick,
            ::onMenuItemDeleteClick,
            ::onMenuItemEditClick
        ).apply { submitList(movies) }
        binding.recyclerListMovies.adapter = movieAdapter
    }

    private fun onMovieClick(movie: Movie) {
        findNavController().navigate(
            ListMoviesFragmentDirections.goToDetailMovieFragment(movie)
        )
    }

    private fun onMenuItemDeleteClick(movie: Movie, position: Int) {
        positionDeleted = position
        viewModel.delete(movie)
    }

    private fun onMenuItemEditClick(movie: Movie) {
        findNavController().navigate(
            ListMoviesFragmentDirections.goToMovieManagerFragment(movie)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllMoviesByName()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}