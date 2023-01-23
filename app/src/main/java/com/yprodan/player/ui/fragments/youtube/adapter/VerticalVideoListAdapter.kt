package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BaseRecyclerAdapter
import com.yprodan.player.domain.entity.VideoModel

class VerticalVideoListAdapter(
    adapterItems: ArrayList<VideoModel>,
    callback: (VideoModel) -> Unit
) :
    BaseRecyclerAdapter<VideoModel, VerticalVideoListViewHolder>(adapterItems, callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerticalVideoListViewHolder {
        return VerticalVideoListViewHolder.from(parent)
    }
}