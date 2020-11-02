package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.ListChildVideoBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemChildVideoAdapter
import com.thin.music.model.ItemSearchOnline

class ListVideoChildFragment : Fragment(), OpenVideoOnlineFragment.ICheckNextVideo,
    ItemChildVideoAdapter.IChildVideo {
    private lateinit var binding: ListChildVideoBinding
    private var openVideo: OpenVideoOnlineFragment? = null
    var index = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListChildVideoBinding.inflate(
            inflater, container, false
        )
        openVideo = OpenVideoOnlineFragment()
        openVideo?.isCheck = this
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter = ItemChildVideoAdapter(this)
        binding.prg.visibility = View.GONE
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listVideo.observe(this, androidx.lifecycle.Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
            openVideo?.isCheck = this
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
        val data = (activity as MainActivity).getModel().listVideo.value!![position]
        (activity as MainActivity).getModel().getLinkVideo(
            data.linkMusic
        )
    }

    override fun setColor(position: Int): Int? {
//        if (position == index) {
//            return Color.parseColor("#FFECB3")
//        }
        return null
    }


    override fun isCheckNextVieo() {
        TODO("Not yet implemented")
    }


}