package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BindableViewHolder
import com.yprodan.player.databinding.ItemHorizontalPlaylistBinding
import com.yprodan.player.domain.entity.VideoModel

class HorizontalVideoListViewHolder private constructor(private val binding: ItemHorizontalPlaylistBinding) :
    BindableViewHolder<VideoModel>(binding.root) {
    override fun bind(model: VideoModel) {
        binding.model = model
    }

    companion object {
        fun from(parent: ViewGroup): HorizontalVideoListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemHorizontalPlaylistBinding.inflate(layoutInflater, parent, false)
            return HorizontalVideoListViewHolder(binding)
        }
    }
}