package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.LoginFragmentBinding
import com.example.appmusic.view.MainActivity

class LoginFrament : Fragment(), View.OnClickListener {
    private lateinit var binding: LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(
            inflater, container, false
        )
        binding.register.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).openRegester()
    }
}