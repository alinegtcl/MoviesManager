package com.luisitolentino.moviesmanager.presentation.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.luisitolentino.moviesmanager.R
import com.luisitolentino.moviesmanager.databinding.TileMovieBinding
import com.luisitolentino.moviesmanager.domain.model.Movie

class MovieAdapter(
    private val onMovieClickListener: (Movie) -> Unit,
    private val onMenuItemDeleteClick: (Movie) -> Unit,
    private val onMenuItemEditClick: (Movie) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Movie, MovieAdapter.MovieViewHolder>(differCallback) {

    private lateinit var binding: TileMovieBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        binding = TileMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(binding: TileMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                textMovieName.text = movie.name
                textMovieGenre.text = movie.movieGenre
                textMovieScore.text =
                    if (movie.score!! < 0) "Ainda não avaliado" else "Nota ${movie.score}"
                imageMovieEditMenu.setOnClickListener {
                    showPopUpMenu(binding, movie)
                }
                root.setOnClickListener { onMovieClickListener(movie) }
            }
        }

        private fun showPopUpMenu(movieTile: TileMovieBinding, movie: Movie) {
            val popupMenu = PopupMenu(movieTile.root.context, movieTile.imageMovieEditMenu)
            popupMenu.inflate(R.menu.menu_movie_manager)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemMenuMovieEdit -> onMenuItemEditClick(movie)
                    R.id.itemMenuMovieDelete -> onMenuItemDeleteClick(movie)
                }
                false
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}