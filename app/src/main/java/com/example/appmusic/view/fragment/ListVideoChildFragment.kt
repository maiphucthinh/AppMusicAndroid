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

class ListVideoChildFragment : Fragment(),
    ItemChildVideoAdapter.IChildVideo {
    private lateinit var binding: ListChildVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListChildVideoBinding.inflate(
            inflater, container, false
        )
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter = ItemChildVideoAdapter(this)
        binding.prg.visibility = View.GONE
        (activity as MainActivity).getModel().getSuggestionsVideo()
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSuggestionsVideo.observe(this, androidx.lifecycle.Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })
    }
    fun nextVideo(){
        val data = (activity as MainActivity).getModel().listSuggestionsVideo.value!![0]
        (activity as MainActivity).getModel().getLinkVideo(
            data.linkMusic
        )
        (activity as MainActivity).getModel().getSuggestionsVideo()
    }

    override fun getSizeVideo(): Int {
        if ((activity as MainActivity).getModel().listSuggestionsVideo.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listSuggestionsVideo.value!!.size
    }

    override fun getListVideo(position: Int): ItemSearchOnline {
        return (activity as MainActivity).getModel().listSuggestionsVideo.value!![position]
    }

    override fun setOnClickItemVideo(position: Int) {
        val data = (activity as MainActivity).getModel().listSuggestionsVideo.value!![position]
        (activity as MainActivity).getModel().getLinkVideo(
            data.linkMusic
        )
        (activity as MainActivity).getModel().getSuggestionsVideo()
    }
}