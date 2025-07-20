package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.ymuratov.jm_test_project.R
import dev.ymuratov.jm_test_project.core.ui.utils.toImageUrl
import dev.ymuratov.jm_test_project.databinding.ItemMovieBinding
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel

class MoviesAdapter : ListAdapter<MovieItemModel, MovieViewHolder>(MovieDiffCallback()) {

    var onMovieClickListener: ((MovieItemModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.moviePosterIV.load(item.posterPath.toImageUrl()) {
            crossfade(true)
            placeholder(R.drawable.ic_poster_placeholder)
            error(R.drawable.ic_poster_placeholder)
        }
        holder.binding.root.setOnClickListener { onMovieClickListener?.invoke(item) }
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

class MovieDiffCallback : DiffUtil.ItemCallback<MovieItemModel>() {
    override fun areItemsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean = oldItem == newItem
}