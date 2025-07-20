package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ymuratov.jm_test_project.databinding.ItemGenreBinding
import dev.ymuratov.jm_test_project.feature.movies.domain.model.GenreModel
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel

class GenresAdapter : ListAdapter<GenreModel, GenreViewHolder>(GenreDiffCallback()) {
    
    var onGenreMoreClickListener: ((GenreModel) -> Unit)? = null
    var onMovieClickListener: ((MovieItemModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = getItem(position)

        val rvAdapter = if (item.movies.isEmpty()) {
            MoviesShimmerAdapter().apply {
                submitList(List(3) { "" })
            }
        } else {
            MoviesAdapter().apply {
                onMovieClickListener = this@GenresAdapter.onMovieClickListener
                submitList(item.movies)
            }
        }
        with(holder.binding) {
            genreNameTV.text = item.name
            genreMoreButton.setOnClickListener { onGenreMoreClickListener?.invoke(item) }
            genreMoviesRV.adapter = rvAdapter
        }

    }
}

class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

class GenreDiffCallback : DiffUtil.ItemCallback<GenreModel>() {
    override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean = oldItem == newItem
}