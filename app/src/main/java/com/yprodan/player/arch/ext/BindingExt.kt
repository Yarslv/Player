package com.yprodan.player.arch.ext

import android.net.Uri
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.yprodan.player.R
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.ui.fragments.home.player.PlayerState
import com.yprodan.player.ui.fragments.home.player.SlidePanelState
import com.yprodan.player.util.MediaMetaDataRetrieverDecorator

@BindingAdapter("setCustomDivider")
fun RecyclerView.setCustomDivider(@DrawableRes res: Int) {
    val decor = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
    val drawable = ContextCompat.getDrawable(context, res)
    drawable?.let { decor.setDrawable(it) }
    addItemDecoration(decor)
}

@BindingAdapter("loadAlbumCover")
fun AppCompatImageView.loadAlbumCover(uri: Uri) {
    if (uri != Uri.EMPTY) {

        val m = MediaMetaDataRetrieverDecorator(context).apply {
            setDataSource(uri)
        }

        Glide.with(this).load(m.getEmbeddedPictureOrEmpty())
            .error(R.drawable.ic_melody)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}

@BindingAdapter("loadVideoThumb")
fun AppCompatImageView.loadVideoThumb(model: VideoModel?) {
    model?.let {
        Glide.with(this).load(it.imageUrl)
            .error(R.drawable.ic_melody)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(this)
    }

}

@BindingAdapter("duration")
fun AppCompatTextView.duration(milis: Int) {
    text = milis.toTime()
}


@BindingAdapter("setPanelState")
fun ConstraintLayout.setState(state: SlidePanelState) {
    visibility = when (state) {
        SlidePanelState.Hided -> View.GONE
        SlidePanelState.Collapsed -> View.VISIBLE
        SlidePanelState.Expanded -> View.VISIBLE
    }
}


@BindingAdapter("setPanelState")
fun SlidingUpPanelLayout.setState(state: SlidePanelState) {
    when (state) {
        SlidePanelState.Hided -> {
            this.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        }
        SlidePanelState.Collapsed -> {
            visibility = View.VISIBLE
            this.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
        SlidePanelState.Expanded -> {
            visibility = View.VISIBLE
            this.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }
    }
}

@BindingAdapter("hide")
fun BottomNavigationView.hide(state: SlidePanelState) {
    visibility = when (state) {
        SlidePanelState.Hided -> View.VISIBLE
        SlidePanelState.Collapsed -> View.VISIBLE
        SlidePanelState.Expanded -> View.INVISIBLE
    }
}

@BindingAdapter("setPlayPauseIcon")
fun AppCompatImageButton.setPlayPauseIcon(state: PlayerState) {
    when (state) {
        PlayerState.PlayingYoutube, PlayerState.PlayingMP3 -> this.setImageDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.ic_pause
            )
        )
        PlayerState.PausingYoutube, PlayerState.PausedMP3 -> this.setImageDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.ic_play
            )
        )
        else -> {}
    }
}

@BindingAdapter("setRefresh")
fun SwipeRefreshLayout.setRefresh(callback: () -> Unit) {
    setOnRefreshListener {
        callback()
        isRefreshing = false
    }
}