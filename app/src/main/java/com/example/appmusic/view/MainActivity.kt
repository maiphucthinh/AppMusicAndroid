package com.example.appmusic.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter
import com.example.appmusic.view.fragment.*
import com.example.appmusic.view.viewpager.MainFragment
import com.example.appmusic.view.viewpager.ParentPageArtist
import com.example.appmusic.view.viewpager.ParentSearchSong
import com.example.appmusic.viewmodel.SongViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener, MediaOnline.IMusicOnline,
    SeekBar.OnSeekBarChangeListener, ChildDiscoverChartAdapter.IChart,
    MediaPlayer.OnCompletionListener, RadioGroup.OnCheckedChangeListener {
    private var listSongs: MutableList<ItemSong>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private var isCheck = true
    private var isBegin = true
    private var isRandomSong = false
    private var asyPlay: MyAsyn? = null
    private var myTimer: MyTimer? = null
    private var index = 0
    private var number = 0
    private lateinit var format: SimpleDateFormat
    private var songService: SongService? = null
    private lateinit var conn: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = SongViewModel()
        format = SimpleDateFormat("mm:ss")
        addMain()
        addContentChild()
        model.getChart()
        binding.play.setOnClickListener(this)
        binding.out.setOnClickListener(this)
        binding.childPlayAndPause.setOnClickListener(this)
        binding.sb.setOnSeekBarChangeListener(this)
        binding.listSong.setOnClickListener(this)
        binding.next.setOnClickListener(this)
        binding.childPrevious.setOnClickListener(this)
        binding.childNext.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.menu.setOnClickListener(this)
        binding.repeat.setOnClickListener(this)
        binding.timer.setOnClickListener(this)
        binding.closeMenu.setOnClickListener(this)
        binding.closeTimer.setOnClickListener(this)
        binding.grTime.setOnCheckedChangeListener(this)
        binding.random.setOnClickListener(this)
        binding.childRemote.setOnClickListener(this)
        binding.showArtistDialig.setOnClickListener(this)
        binding.slidingTimer.setDragView(binding.closeTimer)
        createConnectionToService()
        songService?.player()?.isLooping = false
        async()
        register()
    }


    fun addContentChild() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.content_child,
            ImageLyricFragment(),
            ImageLyricFragment::class.java.name
        )
        transaction.commit()
    }

    private fun createConnectionToService() {
        conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                songService = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                songService = (service as SongService.MyBinder).service
                songService!!.inter = this@MainActivity
                songService!!.model = model
            }

        }
        val intent = Intent()
        intent.setClass(this, SongService::class.java)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    private fun register() {
        model.linkSong.observe(this, Observer {
            index = songService!!.currentPossition
            setSoure(it.link)
            model.getAllArtistSong(it.linkArtist)
            setValues()
        })
        model.chart.observe(this, Observer {
            listSongs = model.chart.value!![1].values
            if (songService != null) {
                songService!!.itemSong = model.chart.value!![1].values
            }
            setValues()
        })
    }


    fun setSoure(linkSong: String) {
        val img = listSongs!!.get(index).linkImage
        val songName = listSongs!!.get(index).songName
        val artistName = listSongs!!.get(index).artistName
        val song = ItemSong(img, songName, artistName, linkSong)
        asyPlay?.cancel(true)
        asyPlay = null
        songService?.release()
        songService?.setDataSoure(song)
    }

    private fun async() {
        val asyn = object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String {
                dfasfasf()
                return ""
            }

        }
        asyn.execute()
    }

    private fun dfasfasf() {
//        val c =
//            Jsoup.connect("https://chiasenhac.vn/ca-si/blackpink-zsswzc7vq91vt2.html?tab=album")
//                .get()
//        val els = c.select("section.album")
//        val linkArtist = els[0].select("a").attr("href")
//        val artistName = els[0].select("a").text()
//        val authorName = els[1].select("a").text()
//        val linkAuthor = els[1].select("a").attr("href")
//        val lyrics = c.select("div.tab-content.tab-lyric").text()
    }

    fun addMain() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        tran.add(R.id.frame, MainFragment(), MainFragment::class.java.name)
        tran.commit()
    }


    fun getModel(): SongViewModel {
        return model
    }


    override fun onBackPressed() {
        val tran = supportFragmentManager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null && fr is OpenVideoOnlineFragment) {
            tran.remove(fr)
        }
        isCheck = true
        binding.slidingPlaylist.visibility = View.VISIBLE
        binding.sliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        binding.playList.visibility = View.VISIBLE
        super.onBackPressed()
    }

    private fun openLogin() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        tran.add(R.id.frame, LoginFrament(), LoginFrament::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun openRegester() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        tran.add(R.id.frame, RegisterFragment(), RegisterFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun openChildListTheme() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        tran.add(R.id.frame, ChildListThemeFragment(), ChildListThemeFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun openVideo(position: Int) {
        songService?.pause()
        binding.play.setImageResource(R.drawable.baseline_play_circle_outline_white_24dp)
        binding.childPlayAndPause.setImageResource(R.drawable.baseline_play_circle_outline_white_24dp)
        binding.slidingPlaylist.visibility = View.GONE
        binding.playList.visibility = View.GONE
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val reg = OpenVideoOnlineFragment()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        val b = Bundle()
        b.putInt("POSITION", position)
        reg.arguments = b
        tran.add(R.id.frame, reg, OpenVideoOnlineFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()

    }

    fun openParentPageArtist() {
        isCheck = true
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        tran.add(R.id.frame, ParentPageArtist(), ParentPageArtist::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    fun openListTheme() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        tran.add(R.id.frame, ListThemeFragment(), ListThemeFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }


    fun openItemChildListSongFragment(position: Int) {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val reg = ItemChildListSongFragment()
        val fr = findCurrentFragment()
        if (fr != null) {
            tran.hide(fr)
        }
        val b = Bundle()
        b.putInt("POSITION", position)
        reg.arguments = b
        tran.add(R.id.frame, reg, ItemChildListSongFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }

    private fun findCurrentFragment(): Fragment? {
        val frs = supportFragmentManager.fragments
        if (frs == null) {
            return null
        }
        for (fr in frs) {
            if (fr != null && fr.isVisible || fr == OpenVideoOnlineFragment()) {
                return fr
            }
        }
        return null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.play -> {
                setClickButtonPlay()
            }
            R.id.child_play_and_pause -> {
                setClickButtonPlay()
            }
            R.id.out -> {
                binding.sliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
            R.id.list_song -> {
                val fg = supportFragmentManager.fragments
                for (i in fg) {
                    if (i is ImageLyricFragment) {
                        i.setCurrentIndex(0)
                    }
                }
            }
            R.id.child_previous -> {
                previousSong()
            }
            R.id.child_next -> {
                nextSong()
            }
            R.id.back -> {
                previousSong()
            }
            R.id.next -> {
                nextSong()
            }
            R.id.menu -> {
                binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
            R.id.timer -> {
                binding.slidingTimer.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
            R.id.close_menu -> {
                binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
            R.id.close_timer -> {
                binding.slidingTimer.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
            R.id.repeat -> {
                when (number) {
                    0 -> {
                        number = 1
                        binding.repeat.setImageResource(R.drawable.baseline_repeat_red_500_24dp)
                    }
                    1 -> {
                        number = 2
                        binding.repeat.setImageResource(R.drawable.baseline_repeat_one_red_500_24dp)
                    }
                    2 -> {
                        number = 0
                        binding.repeat.setImageResource(R.drawable.baseline_repeat_white_24dp)
                    }
                }
            }
            R.id.random -> {
                if (!isRandomSong) {
                    binding.random.setImageResource(R.drawable.baseline_swap_calls_red_500_24dp)
                    isRandomSong = true
                } else {
                    binding.random.setImageResource(R.drawable.baseline_swap_calls_white_24dp)
                    isRandomSong = false
                }
            }
            R.id.child_remote -> {
                binding.sliding.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
            R.id.show_artist_dialig -> {
                binding.sliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                binding.slidingTimer.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                openParentPageArtist()
            }
        }
    }


    fun initMedia(
        position: Int,
        listSong: MutableList<ItemSong>
    ) {
        if (songService != null) {
            songService!!.itemSong = listSong
        }
        listSongs = listSong
        index = position
        songService!!.currentPossition =index

        binding.sliding.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        binding.play.setImageResource(
            R.drawable.baseline_pause_circle_outline_white_24dp
        )
        binding.childPlayAndPause.setImageResource(
            R.drawable.baseline_pause_circle_outline_white_24dp
        )
        setValues()

    }

    private fun previousSong() {
        if (listSongs == null) {
            return
        }
        if (index == 0) {
            index = listSongs?.size!! - 1
            songService!!.currentPossition =index
        }
        index = index - 1
        songService!!.currentPossition = index
        model.linkSong(listSongs?.get(index)!!.linkSong)
        setValues()
    }


    private fun nextSong() {
        if (listSongs == null) {
            return
        }
        if (number == 1) {
            if (index == listSongs?.size!! - 1) {
                index = 0
                songService!!.currentPossition =index
            }
            index += 1
            songService!!.currentPossition =index
            model.linkSong(listSongs?.get(index)!!.linkSong)
            binding.play.setImageResource(
                R.drawable.baseline_pause_circle_outline_white_24dp
            )
            binding.childPlayAndPause.setImageResource(
                R.drawable.baseline_pause_circle_outline_white_24dp
            )
            setValues()
        } else {
            if (index != listSongs?.size!! - 1) {
                index += 1
                songService!!.currentPossition =index
                model.linkSong(listSongs?.get(index)!!.linkSong)
                binding.play.setImageResource(
                    R.drawable.baseline_pause_circle_outline_white_24dp
                )
                binding.childPlayAndPause.setImageResource(
                    R.drawable.baseline_pause_circle_outline_white_24dp
                )
                setValues()
            } else {
                binding.play.setImageResource(R.drawable.baseline_play_circle_outline_white_24dp)
                binding.childPlayAndPause.setImageResource(R.drawable.baseline_play_circle_outline_white_24dp)
            }
        }

    }

    private fun setClickButtonPlay() {
        if (listSongs == null) {
            return
        }
        if (isBegin) {
            model.linkSong(listSongs?.get(index)!!.linkSong)
            isBegin = false
            binding.play.setImageResource(
                R.drawable.baseline_pause_circle_outline_white_24dp
            )
            binding.childPlayAndPause.setImageResource(
                R.drawable.baseline_pause_circle_outline_white_24dp
            )
        }
        if (songService?.player() != null) {
            if (songService?.player()!!.isPlaying) {
                binding.childPlayAndPause.setImageResource(
                    R.drawable.baseline_play_circle_outline_white_24dp
                )
                binding.play.setImageResource(
                    R.drawable.baseline_play_circle_outline_white_24dp
                )
                songService?.pause()
            } else {
                binding.play.setImageResource(
                    R.drawable.baseline_pause_circle_outline_white_24dp
                )
                binding.childPlayAndPause.setImageResource(
                    R.drawable.baseline_pause_circle_outline_white_24dp
                )
                songService?.play()
            }
        }
    }

    private fun setValues() {
        val songName = listSongs?.get(index)!!.songName
        val artistName = listSongs?.get(index)!!.artistName
        val linkImage = listSongs?.get(index)!!.linkImage
        binding.songName.text = songName
        binding.singerName.text = artistName
        binding.childSongName.text = songName
        binding.childSingerName.text = artistName
        binding.songNameDialog.text = songName
        binding.artistNameDialog.text = artistName
        setImageSong(binding.imgChild, linkImage)
        setImageSong(binding.imageSongDialog, linkImage)
    }

    private fun setImageSong(img: ImageView, link: String?) {
        if (link == null || link == "") {
            binding.imgChild.setImageResource(R.drawable.mylove1)
        } else {
            Glide.with(img)
                .load(
                    Uri.parse(
                        link
                    )
                )
                .error(R.drawable.mylove1)
                .into(img)
        }
    }

    fun setImageForTabImageSong(img: ImageView) {
        val linkImage = listSongs?.get(index)!!.linkImage
        setImageSong(img, linkImage)
    }

    override fun onPrepared() {
        songService?.player()?.setOnCompletionListener(this)
        startAsyn()
    }

    private fun startAsyn() {
        if (!songService!!.isPrepare()) {
            return
        }
        if (asyPlay != null) {
            asyPlay!!.isRunning = false
        }
        val total = songService?.getTotalTime()
        binding.sumTime.text = format.format(total)
        asyPlay = MyAsyn()
        asyPlay!!.executeOnExecutor(Executors.newFixedThreadPool(1))
    }

    override fun getSizeChart(): Int {
        if (listSongs == null) {
            return 0
        }
        return listSongs!!.size
    }

    override fun getListChart(position: Int): ItemSong {
        return listSongs!![position]
    }

    override fun setOnclickItemChart(position: Int) {
        index = position
        model.linkSong(
            listSongs!![position].linkSong
        )
        binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        setValues()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (number == 2) {
            songService?.play()
        } else {
            if (isRandomSong) {
                randomSong()
            } else {
                if (number == 0) {
                    nextSong()
                }
            }
        }
    }

    private fun randomSong() {
        if (listSongs != null) {
            val size = listSongs!!.size
            val indexChild = Random.nextInt(size)
            index = indexChild
            model.linkSong(
                listSongs!![indexChild].linkSong
            )
            setValues()
        }
    }

    fun getListSong(): MutableList<ItemSong>? {
        return listSongs
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rg1 -> {
                myTimer = null
                myTimer = MyTimer(true)
                myTimer!!.execute(30 * 60)
            }
            R.id.rg2 -> {
                myTimer = null
                myTimer = MyTimer(true)
                myTimer!!.execute(60 * 60)
            }
            R.id.rg3 -> {
                myTimer = null
                myTimer = MyTimer(true)
                myTimer!!.execute(90 * 60)
            }
            R.id.rg4 -> {
                myTimer = null
                myTimer = MyTimer(true)
                myTimer!!.execute(120 * 60)
            }
        }
    }

    inner class MyAsyn : AsyncTask<Void, Int?, Void?>() {
        var isRunning = true
        override fun doInBackground(vararg params: Void?): Void? {
            while (isRunning) {
                publishProgress(songService!!.getCurentTime())
                SystemClock.sleep(500)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            binding.currenTime.text = (format.format(values[0]))
            binding.sb.progress = values[0]!! * 100 / songService!!.getTotalTime()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            songService?.player()!!.seekTo(
                progress.toLong() * songService!!.getTotalTime() / 100,
                MediaPlayer.SEEK_NEXT_SYNC
            )
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        asyPlay?.isRunning = false
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        startAsyn()
    }

    inner class MyTimer : AsyncTask<Int, Void, Boolean> {
        var isRunning: Boolean

        constructor(isRunning: Boolean) {
            this.isRunning = isRunning
        }

        override fun doInBackground(vararg params: Int?): Boolean {
            for (i in 0..params[0]!!) {
                if (!isRunning) {
                    return false
                }
                SystemClock.sleep(1000)
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if (result == true) {
                songService?.pause()
                binding.rg1.isChecked = false
                binding.rg2.isChecked = false
                binding.rg3.isChecked = false
                binding.rg4.isChecked = false
                binding.childPlayAndPause.setImageResource(
                    R.drawable.baseline_play_circle_outline_white_24dp
                )
                binding.play.setImageResource(
                    R.drawable.baseline_play_circle_outline_white_24dp
                )
                myTimer = null
            }
        }


    }

    fun fullCreen(view: VideoView) {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val parent = view.parent as View
        val paramParent = parent.layoutParams
        paramParent.width = metrics.widthPixels
        paramParent.height = metrics.heightPixels
        parent.layoutParams = paramParent
        parent.requestLayout()

        val params =
            view.layoutParams
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels
        view.layoutParams = params
        view.requestLayout()
    }

    fun halfCreen(videoView: VideoView) {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val params =
            videoView.layoutParams
        params.width = (300 * metrics.density).toInt()
        params.height = (250 * metrics.density).toInt()
        videoView.layoutParams = params
    }

    fun getCountMusic(): Int {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projectment = arrayOf("count(*) as c")
        val cursor = contentResolver.query(
            uri,
            projectment,
            null,
            null,
            null
        )!!
        cursor.moveToFirst()
        val indexC = cursor.getColumnIndex("c")
        val number = cursor.getInt(indexC)
        cursor.close()
        return number
    }

    fun getParenFragmentSearch(): ParentSearchSong? {
        val fg = supportFragmentManager.fragments
        for (i in fg) {
            if (i is MainFragment) {
                return i.getParentSearchSong()
            }
        }
        return null
    }
}