package com.yprodan.player.ui.fragments.youtube.adapter

import android.view.ViewGroup
import com.yprodan.player.arch.adapter.BaseRecyclerAdapter
import com.yprodan.player.domain.entity.VideoModel

class SearchedVideoListAdapter(
    adapterItems: ArrayList<VideoModel>,
    callback: (VideoModel) -> Unit
) :
    BaseRecyclerAdapter<VideoModel, SearchedVideoListViewHolder>(adapterItems, callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchedVideoListViewHolder {
        return SearchedVideoListViewHolder.from(parent)
    }
}


