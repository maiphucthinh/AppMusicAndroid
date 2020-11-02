package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.MvPageBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.MVAdapter
import com.thin.music.model.ItemSearchOnline

class MVFragment : Fragment(), MVAdapter.IVideo {
    private lateinit var binding: MvPageBinding
    private var isCheckLoading = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MvPageBinding.inflate(
            inflater, container, false
        )
        (activity as MainActivity).getModel().getVideo()
        register()
        binding.rcMv.layoutManager = LinearLayoutManager(context)
        binding.rcMv.adapter = MVAdapter(this)

        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().isRuningVideo.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            } else
                binding.prg.visibility = View.GONE
        })

        (activity as MainActivity).getModel().listVideo.observe(this, Observer {
            binding.rcMv.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getSizeVideo(): Int {
        if ((activity as MainActivity).getModel().listVideo.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listVideo.value!!.size
    }

    override fun getListVideo(position: Int): ItemSearchOnline {
        return (activity as MainActivity).getModel().listVideo.value!![position]
    }

    override fun setOnClickItemVideo(position: Int) {
        val linkVideo = (activity as MainActivity).getModel().listVideo.value!![position].linkMusic
        (activity as MainActivity).getModel().getLinkVideo(
            linkVideo
        )
        (activity as MainActivity).openVideo(position)

    }
}