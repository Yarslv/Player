package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BaseRecyclerAdapter
import com.yprodan.player.domain.entity.VideoModel

class HorizontalVideoListAdapter(
    adapterItems: ArrayList<VideoModel>,
    callback: (VideoModel) -> Unit
) :
    BaseRecyclerAdapter<VideoModel, HorizontalVideoListViewHolder>(adapterItems, callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalVideoListViewHolder {
        return HorizontalVideoListViewHolder.from(parent)
    }
}