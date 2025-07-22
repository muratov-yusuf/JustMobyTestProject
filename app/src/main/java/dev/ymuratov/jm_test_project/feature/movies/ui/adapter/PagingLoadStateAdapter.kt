package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ymuratov.jm_test_project.databinding.ItemLoadStateBinding

class PagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        with(holder.binding) {
            retryButton.setOnClickListener { retry() }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorText.isVisible = loadState is LoadState.Error
        }
    }
}

class LoadStateViewHolder(val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root)
