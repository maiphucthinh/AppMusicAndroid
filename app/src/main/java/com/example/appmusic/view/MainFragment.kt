package com.example.appmusic.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.appmusic.R
import com.example.appmusic.databinding.MainFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.fragment.LoginFrament
import com.example.appmusic.view.fragment.RegisterFragment
import com.example.appmusic.view.fragment.SearchSong
import com.example.appmusic.view.viewpager.MainActivity
import com.example.appmusic.viewmodel.SongViewModel
import androidx.core.view.isVisible as isVisible1

class MainFragment : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MainFragmentBinding
    private lateinit var model: SongViewModel
    private var isCheck = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_fragment)
        model = SongViewModel()
//        binding.searching.visibility = View.GONE
        binding.searching.setOnClickListener(this)
        binding.avatar.setOnClickListener(this)
        addMain()
    }

    fun addMain() {
        isCheck = true
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        tran.add(R.id.frame, MainActivity(), MainActivity::class.java.name)
        tran.commit()
    }

    fun openMain() {
        binding.search.setText("")
        isCheck = true
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fgMain = manager.findFragmentByTag(SearchSong::class.java.name)
        tran.remove(fgMain!!)
        tran.add(R.id.frame, MainActivity(), MainActivity::class.java.name)
        tran.commit()
    }

    fun openSearchSong() {
        isCheck = false
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()

        val fgMain = manager.findFragmentByTag(
            MainActivity::class.java.name
        )
        tran.remove(fgMain!!)
        tran.add(R.id.frame, SearchSong(), SearchSong::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun getModel(): SongViewModel {
        return model
    }

    fun getSize(): Int {
        if (model.songData.value == null) {
            return 0
        }
        return model.songData.value!!.size
    }

    fun getItemSong(position: Int): ItemSong {
        return model.songData.value!![position]
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.searching -> {
                val content = binding.search.text.toString()
                if (content.equals("")) {
                    return
                }
                model.searchSong(content)
                if (isCheck) {
                    openSearchSong()
                }
                binding.search.setText("")
            }
            R.id.avatar -> {
                openLogin()
            }
        }

    }

    private fun openLogin() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fgMain = manager.findFragmentByTag(
            MainActivity::class.java.name
        )
        tran.remove(fgMain!!)
        tran.add(R.id.frame, LoginFrament(), LoginFrament::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun openRegester() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fgMain = manager.findFragmentByTag(
            LoginFrament::class.java.name
        )
        tran.remove(fgMain!!)
        tran.add(R.id.frame, RegisterFragment(), RegisterFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }
}