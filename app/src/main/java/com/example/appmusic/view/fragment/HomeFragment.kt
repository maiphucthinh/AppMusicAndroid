package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.HomePageBinding
import com.example.appmusic.view.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: HomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(
            inflater, container, false
        )

        binding.numberSong.text = ("" + (activity as MainActivity).getCountMusic())
        return binding.root
    }

}