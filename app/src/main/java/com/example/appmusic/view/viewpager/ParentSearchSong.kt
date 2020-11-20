package com.example.appmusic.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.appmusic.R
import com.example.appmusic.databinding.ParentSearchSongBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.Utils
import com.example.appmusic.view.fragment.TabAllSearchSongFragment


class ParentSearchSong : Fragment(), View.OnClickListener{
    private lateinit var binding: ParentSearchSongBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ParentSearchSongBinding.inflate(
            inflater, container, false
        )
        binding.vpg.adapter = ParentSearchSongAdapter(
            childFragmentManager
        )
        binding.tab.setupWithViewPager(
            binding.vpg
        )
        binding.searching.setOnClickListener(this)
        binding.avatar.setOnClickListener(this)
        register()

        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSearchAll.observe(this, Observer {
            if (it == null || it[0].values.size == 0) {
                binding.bgr.setBackgroundResource(
                    R.drawable.bg_notification
                )
            } else {
                binding.bgr.setBackgroundResource(
                    R.drawable.bgmusic
                )
            }
        })
    }

    fun setCLickNext(position: Int) {
        binding.vpg.currentItem = position

    }

    override fun onClick(v: View) {
        Utils.hideKeyboard(activity!!)
        when (v.id) {
            R.id.searching -> {
                val content = binding.search.text.toString()
                if (content == "") {
                    return
                }

                (activity as MainActivity).getModel().getSearchAll(content)
                binding.search.setText("")
            }
            R.id.avatar -> (activity as MainActivity).openLogin()
        }
    }

}