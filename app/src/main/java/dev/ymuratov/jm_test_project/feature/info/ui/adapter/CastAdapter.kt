package dev.ymuratov.jm_test_project.feature.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.ymuratov.jm_test_project.R
import dev.ymuratov.jm_test_project.core.ui.utils.toImageUrl
import dev.ymuratov.jm_test_project.databinding.ItemCastBinding
import dev.ymuratov.jm_test_project.feature.info.domain.model.CastModel

class CastAdapter : ListAdapter<CastModel, CastViewHolder>(CastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(inflater, parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            castPhotoIV.load(item.profilePath?.toImageUrl()) {
                crossfade(true)
                placeholder(R.drawable.ic_poster_placeholder)
                error(R.drawable.ic_poster_placeholder)
            }
            castNameTV.text = item.name
        }
    }
}

class CastViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)
class CastDiffCallback : DiffUtil.ItemCallback<CastModel>() {
    override fun areItemsTheSame(oldItem: CastModel, newItem: CastModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CastModel, newItem: CastModel): Boolean = oldItem == newItem
}