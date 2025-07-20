package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ymuratov.jm_test_project.databinding.ItemMovieShimmerBinding

class MoviesShimmerAdapter : ListAdapter<String, MovieShimmerViewHolder>(MovieShimmerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieShimmerBinding.inflate(inflater, parent, false)
        return MovieShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieShimmerViewHolder, position: Int) {
        holder.binding.movieShimmerContainer.startShimmerAnimation()
    }
}

class MovieShimmerViewHolder(val binding: ItemMovieShimmerBinding) : RecyclerView.ViewHolder(binding.root)

class MovieShimmerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}