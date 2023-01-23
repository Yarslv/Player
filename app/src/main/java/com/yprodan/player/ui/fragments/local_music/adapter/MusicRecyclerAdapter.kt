package com.yprodan.player.ui.fragments.local_music.adapter

import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BaseRecyclerAdapter
import com.yprodan.player.domain.entity.MusicModel

class MusicRecyclerAdapter
    (adapterItems: ArrayList<MusicModel>, callback: (MusicModel) -> Unit) :
    BaseRecyclerAdapter<MusicModel, MusicRecyclerViewHolder>(adapterItems, callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicRecyclerViewHolder {
        return MusicRecyclerViewHolder.from(parent)
    }
}



