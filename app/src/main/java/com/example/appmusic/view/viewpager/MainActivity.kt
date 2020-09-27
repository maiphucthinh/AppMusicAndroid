package com.example.appmusic.view.viewpager

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainFragment
import com.example.appmusic.view.viewpager.MainAdapter
import com.example.appmusic.viewmodel.SongViewModel
import org.jsoup.Jsoup

class MainActivity : Fragment() {
    private lateinit var activitys: MainFragment
    private lateinit var binding: ActivityMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMainBinding.inflate(
            inflater, container, false
        )
        activitys = (activity as MainFragment)
        binding.vp.adapter = MainAdapter(activitys.supportFragmentManager)
        binding.tab.setupWithViewPager(
            binding.vp
        )
        setupTabIcons()
        asyn()
        return binding.root
    }


    fun setupTabIcons() {
        binding.tab.getTabAt(0)!!.setIcon(R.drawable.baseline_library_music_white_24dp)
        binding.tab.getTabAt(1)!!.setIcon(R.drawable.baseline_adjust_white_24dp)
        binding.tab.getTabAt(2)!!.setIcon(R.drawable.baseline_slideshow_white_24dp)
//        binding.tab.getTabAt(3)!!.setIcon(R.drawable.baseline_search_white_24dp)

    }

    fun asyn() {
        val asy = object : AsyncTask<String, Void, String>() {
            override fun doInBackground(vararg params: String?): String {
                parseHtml()
                return ""
            }


        }
        asy.execute("")
    }

    fun parseHtml() {
        val songName = "chia tay"
        val doc: org.jsoup.nodes.Document = Jsoup.connect(
            ("https://chiasenhac.vn/tim-kiem?q="
                    + songName.replace(" ", "+"))
                    + "&page_music=" + "1" + "&filter=all"
        ).get()
        val els = doc.select("div.tab-content").first().select("ul.list-unstyled")
        for (i in 0..els.size-1){
           val e = els.get(i)
            val childEls = e.select("li.media")
        }

    }

}