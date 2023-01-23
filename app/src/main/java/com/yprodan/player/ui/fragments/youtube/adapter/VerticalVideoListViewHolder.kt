package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BindableViewHolder
import com.yprodan.player.databinding.ItemVerticalPlaylistBinding
import com.yprodan.player.domain.entity.VideoModel

class VerticalVideoListViewHolder private constructor(private val binding: ItemVerticalPlaylistBinding) :
    BindableViewHolder<VideoModel>(binding.root) {
    override fun bind(model: VideoModel) {
        binding.model = model
    }

    companion object {
        fun from(parent: ViewGroup): VerticalVideoListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemVerticalPlaylistBinding.inflate(layoutInflater, parent, false)
            return VerticalVideoListViewHolder(binding)
        }
    }
}