package dev.ymuratov.jm_test_project.feature.movies.ui.adapter

import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.ymuratov.jm_test_project.R
import dev.ymuratov.jm_test_project.core.ui.CenteredImageSpan
import dev.ymuratov.jm_test_project.core.ui.utils.toImageUrl
import dev.ymuratov.jm_test_project.databinding.ItemMovieExpandedBinding
import dev.ymuratov.jm_test_project.feature.movies.domain.model.MovieItemModel

class MoviesByGenreAdapter : PagingDataAdapter<MovieItemModel, MovieExpandedViewHolder>(MovieExpandedDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieExpandedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieExpandedBinding.inflate(layoutInflater, parent, false)
        return MovieExpandedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieExpandedViewHolder, position: Int) {
        val item = getItem(position)
        val star = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.ic_star)
            ?.apply { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
        val starSpan = CenteredImageSpan(star!!)
        val spannable = SpannableString(" ${item?.voteAverage}${if (item?.adult == true) " • 18+" else ""}")
        spannable.setSpan(starSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        with(holder.binding) {
            movieExpandedPosterIV.load(item?.posterPath?.toImageUrl()) {
                crossfade(true)
                placeholder(R.drawable.ic_poster_placeholder)
                error(R.drawable.ic_poster_placeholder)
            }
            movieExpandedNameTV.text = item?.title
            movieExpandedDetailsTV.setText(spannable, TextView.BufferType.SPANNABLE)
        }
    }
}

class MovieExpandedViewHolder(val binding: ItemMovieExpandedBinding) : RecyclerView.ViewHolder(binding.root)

class MovieExpandedDiffCallback : DiffUtil.ItemCallback<MovieItemModel>() {
    override fun areItemsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MovieItemModel, newItem: MovieItemModel): Boolean = oldItem == newItem
}
