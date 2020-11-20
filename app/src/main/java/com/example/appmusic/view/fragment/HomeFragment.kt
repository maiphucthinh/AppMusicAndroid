package com.example.appmusic.view.fragment

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.R
import com.example.appmusic.databinding.HomePageBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import kotlinx.android.synthetic.main.home_page.view.*

class HomeFragment : Fragment(), View.OnClickListener {
    private var listSong = mutableListOf<ItemSong>()
    private lateinit var binding: HomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(
            inflater, container, false
        )
        resetTotalNumberSong()
        binding.homeSongs.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.home_songs->{
                (activity as MainActivity).openListSongOffLine()
            }
            R.id.home_play_list->{

            }
            R.id.home_album->{

            }
        }
    }
    fun resetTotalNumberSong(){
        binding.numberSong.text = ("" + (activity as MainActivity).getCountMusic())
    }


}