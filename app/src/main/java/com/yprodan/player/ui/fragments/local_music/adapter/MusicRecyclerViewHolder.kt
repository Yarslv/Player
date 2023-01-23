package com.yprodan.player.ui.fragments.local_music.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BindableViewHolder
import com.yprodan.player.databinding.ItemLocalMusicBinding
import com.yprodan.player.domain.entity.MusicModel

class MusicRecyclerViewHolder private constructor(private val binding: ItemLocalMusicBinding) :
    BindableViewHolder<MusicModel>(binding.root) {

    override fun bind(model: MusicModel) {
        binding.model = model
    }

    companion object {
        fun from(parent: ViewGroup): MusicRecyclerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemLocalMusicBinding.inflate(layoutInflater, parent, false)
            return MusicRecyclerViewHolder(binding)
        }
    }
}