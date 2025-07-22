package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ymuratov.jm_test_project.databinding.ItemGenreShimmerBinding

class GenresShimmerAdapter : ListAdapter<String, GenreShimmerViewHolder>(GenreShimmerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreShimmerBinding.inflate(inflater, parent, false)
        return GenreShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreShimmerViewHolder, position: Int) {
        holder.binding.genreShimmerContainer.startShimmerAnimation()
    }
}

class GenreShimmerViewHolder(val binding: ItemGenreShimmerBinding) : RecyclerView.ViewHolder(binding.root)

class GenreShimmerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}