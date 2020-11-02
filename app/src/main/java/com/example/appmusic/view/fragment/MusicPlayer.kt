package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.appmusic.databinding.MusicPlayerBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.viewpager.PlayerAdapter

class MusicPlayer : Fragment() {
    private lateinit var binding: MusicPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MusicPlayerBinding.inflate(
            inflater, container, false
        )
        register()
        binding.vp.adapter = PlayerAdapter(childFragmentManager)
        binding.tb.setupWithViewPager(
            binding.vp
        )
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkSong.observe(this, Observer {
//            Log.d("MainActivity", "observe link song: " + it.link)
//            mediaOnline.release()
//            mediaOnline.setDataSoure(context!!, it.link)
            initPlayer()
        })
    }

    private fun initPlayer() {
        binding.songName.setText("thinh")
    }


}