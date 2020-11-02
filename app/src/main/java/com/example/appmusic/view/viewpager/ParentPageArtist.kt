package com.example.appmusic.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.appmusic.R
import com.example.appmusic.databinding.ParentPageArtistBinding
import com.example.appmusic.view.MainActivity

class ParentPageArtist : Fragment() {
    private lateinit var binding: ParentPageArtistBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ParentPageArtistBinding.inflate(
            inflater, container, false
        )
        binding.vpg.adapter = ParentPageArtistAdapter(
            childFragmentManager
        )
        binding.tab.setupWithViewPager(
            binding.vpg
        )
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listArtistSong.observe(this, Observer {
            val link = (activity as MainActivity).getModel().listArtistSong.value!![0].name
            Glide.with(binding.imgArtist)
                .load(link)
                .error(R.drawable.iconmusic)
                .into(binding.imgArtist)
        })
    }
}