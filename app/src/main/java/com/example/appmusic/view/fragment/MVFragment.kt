package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.DiscoverPageBinding
import com.example.appmusic.databinding.MvPageBinding

class MVFragment : Fragment() {
    private lateinit var binding: MvPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MvPageBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}