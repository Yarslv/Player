package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BindableViewHolder
import com.yprodan.player.databinding.ItemSearchedVideoBinding
import com.yprodan.player.domain.entity.VideoModel

class SearchedVideoListViewHolder private constructor(private val binding: ItemSearchedVideoBinding) :
    BindableViewHolder<VideoModel>(binding.root) {
    override fun bind(model: VideoModel) {
        binding.model = model
    }

    companion object {
        fun from(parent: ViewGroup): SearchedVideoListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemSearchedVideoBinding.inflate(layoutInflater, parent, false)
            return SearchedVideoListViewHolder(binding)
        }
    }
}