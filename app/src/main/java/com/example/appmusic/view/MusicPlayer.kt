package com.example.appmusic.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.appmusic.R
import com.example.appmusic.databinding.MusicPlayerBinding
import com.example.appmusic.view.viewpager.PlayerAdapter

class MusicPlayer : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MusicPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.music_player)
        binding.vp.adapter = PlayerAdapter(supportFragmentManager)
        binding.tb.setupWithViewPager(
            binding.vp
        )
        binding.out.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        finish()
    }
}