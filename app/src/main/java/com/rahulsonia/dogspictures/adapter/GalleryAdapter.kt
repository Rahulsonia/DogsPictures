package com.rahulsonia.dogspictures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rahulsonia.dogspictures.databinding.ImageItemBinding
import com.rahulsonia.dogspictures.model.ImageListItem

class GalleryAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ImageListItem, GalleryAdapter.GalleryViewHolder>(DIFFUTIL) {

    inner class GalleryViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageListItem: ImageListItem) {
            binding.apply {
                if (!imageListItem.breeds.isNullOrEmpty()) {
                    Glide.with(itemView).load(imageListItem.url).centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }
    }

    companion object {
        object DIFFUTIL : DiffUtil.ItemCallback<ImageListItem>() {
            override fun areItemsTheSame(oldItem: ImageListItem, newItem: ImageListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ImageListItem,
                newItem: ImageListItem
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imageListItem = getItem(holder.absoluteAdapterPosition)
        if (imageListItem != null) {
            holder.bind(imageListItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onItemClick(imageListItem: ImageListItem)
    }

}