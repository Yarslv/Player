package com.yprodan.player.ui.fragments.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yprodan.player.ui.fragments.home.HomeFragment
import com.yprodan.player.ui.fragments.local_music.LocalMusicFragment
import com.yprodan.player.ui.fragments.youtube.YoutubeFragment

class HomePageAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragmentsList = listOf(YoutubeFragment(), LocalMusicFragment())

    override fun getItemCount() = fragmentsList.size

    override fun createFragment(position: Int) = fragmentsList[position]
}