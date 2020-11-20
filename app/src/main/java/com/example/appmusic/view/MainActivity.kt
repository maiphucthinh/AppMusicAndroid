package com.example.appmusic.view

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.ConnectivityManager
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.appmusic.MyApp
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.fragment.*
import com.example.appmusic.view.viewpager.MainFragment
import com.example.appmusic.view.viewpager.ParentPageArtist
import com.example.appmusic.view.viewpager.ParentSearchSong
import com.example.appmusic.viewmodel.SongViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import kotlin.random.Random

//gradlew assemblerelease
class MainActivity : AppCompatActivity(), View.OnClickListener, MediaOnline.IMusicMedia,
    SeekBar.OnSeekBarChangeListener,
    RadioGroup.OnCheckedChangeListener {
    private var listSongs: MutableList<ItemSong>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private var isCheck = true
    private var isBegin = true
    private var isDestroyView = false
    private var isCheckMediaType = false
    private var isRandomSong = false
    private var asyPlay: MyAsync? = null
    private var myTimer: MyTimer? = null
    private var currentPosition = 0
    private var number = 0
    private lateinit var format: SimpleDateFormat
    private var songService: SongService? = null
    private lateinit var conn: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDestroyView = false
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
        binding.downloadSongDialig.setOnClickListener(this)
        createConnectionToService()
        model.loadAllMusicOffline(this)
        songService?.playerOnline()?.isLooping = false
        register()
        if (!isCheckInternet()) {
            Toast.makeText(this, "kiểm tra kết nối internet!!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Trực tuyến!!", Toast.LENGTH_LONG).show()
        }
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
        if (Utils.showPermission(
                this, 10,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {

        }

        model.linkSong.observe(this, Observer {
            currentPosition = songService!!.currentPosition
            listSongs!![currentPosition].linkSong = it.link
            setSourceOnline(listSongs!![currentPosition])
            model.getAllArtistSong(it.linkArtist)
            setValues()
        })
        model.lisSongOffline.observe(this, Observer {
            if (songService != null) {
                songService!!.itemSong = model.lisSongOffline.value!!
            }
            listSongs = model.lisSongOffline.value
            if (listSongs?.size != 0 && listSongs != null) {
                setValues()
            }
        })

        MyApp.getAppModel().actionMedia.observe(this, Observer {
            if (it == "PLAY") {
                setImageButtonPause()
            } else
                setImageButtonPlay()
        })
    }


    private fun setSourceOnline(listSong: ItemSong) {
        isCheckMediaType = true
        isBegin = false
        asyPlay?.cancel(true)
        asyPlay = null
        songService?.releaseOnline()
        songService?.setDataSourceOnline(listSong)
    }

    private fun setSourceOffline(itemSong: ItemSong) {
        isBegin = false
        asyPlay?.cancel(true)
        asyPlay = null
        currentPosition = songService!!.currentPosition
        getImageLyricFragment()!!.getImagePlayer().setImage()
        isCheckMediaType = false
        songService!!.setDataSourceOffline(itemSong)
    }

    private fun addMain() {
        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        tran.add(R.id.frame, MainFragment(), MainFragment::class.java.name)
        tran.commit()
    }


    fun getModel(): SongViewModel {
        return model
    }

    fun openListSongOffLine() {
        val tran = supportFragmentManager.beginTransaction()
        val current = findCurrentFragment()
        if (current != null) {
            tran.hide(current)
        }
        tran.add(R.id.frame, ListSongOffline(), ListSongOffline::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
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

    fun openLogin() {
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

    fun openRegister() {
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
        songService?.pauseOnline()
        setImageButtonPlay()
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
        for (fr in frs) {
            if (fr != null && fr.isVisible || fr == OpenVideoOnlineFragment()) {
                return fr
            }
        }
        return null
    }

    private fun getImageLyricFragment(): ImageLyricFragment? {
        val fg = supportFragmentManager.fragments
        for (i in fg) {
            if (i is ImageLyricFragment) {
                return i
            }
        }
        return null
    }

    private fun getHomeFragment(): HomeFragment? {
        val fg = supportFragmentManager.fragments
        for (i in fg) {
            if (i is HomeFragment) {
                return i
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
                getImageLyricFragment()!!.setCurrentIndex(0)
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
                isRandomSong = if (!isRandomSong) {
                    binding.random.setImageResource(R.drawable.baseline_swap_calls_red_500_24dp)
                    true
                } else {
                    binding.random.setImageResource(R.drawable.baseline_swap_calls_white_24dp)
                    false
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
            R.id.download_song_dialig -> {
                val linkSong = listSongs?.get(currentPosition)!!.linkSong
                if (linkSong == null) {
                    Toast.makeText(this, "Không thể tải bài hát!!!", Toast.LENGTH_SHORT).show()
                } else
                    downLoadSong(linkSong)
            }
        }
    }

    fun initMedia(
        position: Int?,
        listSong: MutableList<ItemSong>?,
        isCheck: Boolean = true
    ) {
        val imageLyricFragment = getImageLyricFragment()!!
        imageLyricFragment.setCurrentIndex(1)
        if (listSong != null) {
            imageLyricFragment.getplayList().resetPlaylist()
            songService!!.itemSong = listSong
            listSongs = listSong
            binding.sliding.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            binding.slidingPlaylist.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
        if (position == null) {
            clickButtonRandomSong()
        } else {
            currentPosition = position
            songService!!.currentPosition = currentPosition
        }
        asyPlay?.cancel(true)
        asyPlay = null
        isCheckMediaType = isCheck
        songService?.isCheckCurrentType = isCheck
        isCheckSetSource()
        setImageButtonPause()
        setValues()
    }

    private fun isCheckSetSource() {
        val imageLyricFragment = getImageLyricFragment()!!
        if (isCheckMediaType) {
            songService!!.releaseOffline()
            model.linkSong(
                listSongs!![currentPosition].linkSong
            )
        } else {
            songService!!.releaseOnline()
            setSourceOffline(listSongs!![currentPosition])
            imageLyricFragment.getTabLyric().resetLyric()
        }
    }

    private fun clickButtonRandomSong() {
        currentPosition = Random.nextInt(
            listSongs!!.size
        )
        songService!!.currentPosition = currentPosition
        isRandomSong = true
        binding.random.setImageResource(R.drawable.baseline_swap_calls_red_500_24dp)
    }

    private fun previousSong() {
        if (listSongs == null) {
            return
        }
        if (currentPosition == 0) {
            currentPosition = listSongs?.size!! - 1
            songService!!.currentPosition = currentPosition
        }
        currentPosition -= 1
        songService!!.currentPosition = currentPosition
        isCheckSetSource()
        setValues()
    }


    private fun nextSong() {
        if (listSongs == null) {
            return
        }
        if (isRandomSong) {
            randomSong()
            setImageButtonPause()
            return
        }
        if (number == 1) {
            if (currentPosition == listSongs?.size!! - 1) {
                currentPosition = 0
                songService!!.currentPosition = currentPosition
            }
            currentPosition += 1
            songService!!.currentPosition = currentPosition
            isCheckSetSource()
            setImageButtonPause()
            setValues()
        } else {
            if (currentPosition != listSongs?.size!! - 1) {
                currentPosition += 1
                songService!!.currentPosition = currentPosition
                isCheckSetSource()
                setImageButtonPause()
                setValues()
            } else {
                setImageButtonPlay()
            }
        }

    }

    private fun setImageButtonPause() {
        binding.play.setImageResource(
            R.drawable.baseline_pause_circle_outline_white_24dp
        )
        binding.childPlayAndPause.setImageResource(
            R.drawable.baseline_pause_circle_outline_white_24dp
        )
    }

    private fun setImageButtonPlay() {
        binding.play.setImageResource(
            R.drawable.baseline_play_circle_outline_white_24dp
        )
        binding.childPlayAndPause.setImageResource(
            R.drawable.baseline_play_circle_outline_white_24dp
        )
    }

    private fun setClickButtonPlay() {
        if (listSongs == null) {
            return
        }
        if (isBegin) {
            isCheckSetSource()
            setImageButtonPause()
            return
        }

        if (isCheckMediaType) {
            if (songService?.playerOnline()!!.isPlaying) {
                setImageButtonPlay()
                songService?.pauseOnline()
            } else {
                setImageButtonPause()
                songService?.playOnline()
            }

        } else {
            if (songService?.playerOffLine()!!.isPlaying) {
                setImageButtonPlay()
                songService?.pauseOffline()
            } else {
                setImageButtonPause()
                songService?.playOffline()
            }
        }
    }

    private fun setValues() {
        val songName = listSongs?.get(currentPosition)!!.songName
        val artistName = listSongs?.get(currentPosition)!!.artistName
        val linkImage = listSongs?.get(currentPosition)!!.linkImage
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
        val linkImage = listSongs?.get(currentPosition)!!.linkImage
        setImageSong(img, linkImage)
    }

    override fun onPrepared() {
        startAsync()
    }

    override fun onCompletion() {
        if (number == 2) {
            songService?.playOnline()
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

    private fun startAsync() {
        if (asyPlay != null) {
            asyPlay!!.isRunning = false
        }
        if (isCheckMediaType) {
            if (!songService!!.isPrepareOnline()) {
                return
            }
            val total = songService?.getTotalTimeOnline()
            binding.sumTime.text = format.format(total)
            asyPlay = MyAsync()
            asyPlay!!.executeOnExecutor(Executors.newFixedThreadPool(1))
        } else {
            val total = songService?.getTotalTimeOffline()
            binding.sumTime.text = format.format(total)
            asyPlay = MyAsync()
            asyPlay!!.executeOnExecutor(Executors.newFixedThreadPool(1))
        }
    }

    private fun randomSong() {
        if (listSongs != null) {
            val size = listSongs!!.size
            currentPosition = Random.nextInt(size)
            setValues()
            isCheckSetSource()
        }
    }

    fun getListSong(): MutableList<ItemSong>? {
        return listSongs
    }

    fun getCheckCurrentMedia(): Boolean {
        return isCheckMediaType
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

    inner class MyAsync : AsyncTask<Void, Int?, Void?>() {
        var isRunning = true
        override fun doInBackground(vararg params: Void?): Void? {
            while (isRunning) {
                if (isCheckMediaType) {
                    publishProgress(songService!!.getCurrentTimeOnline())
                } else {
                    publishProgress(songService!!.getCurrentTimeOffline())
                }
                SystemClock.sleep(500)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            if (isCheckMediaType) {
                binding.currenTime.text = (format.format(values[0]))
                binding.sb.progress = values[0]!! * 100 / songService!!.getTotalTimeOnline()
            } else {
                binding.currenTime.text = (format.format(values[0]))
                binding.sb.progress = values[0]!! * 100 / songService!!.getTotalTimeOffline()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            if (isCheckMediaType) {
                songService?.playerOnline()!!.seekTo(
                    progress * songService!!.getTotalTimeOnline() / 100
                )
            } else {
                songService?.playerOffLine()!!
                    .seekTo(progress * songService!!.getTotalTimeOffline() / 100)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        asyPlay?.isRunning = false
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        startAsync()
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
                songService?.pauseOnline()
                binding.rg1.isChecked = false
                binding.rg2.isChecked = false
                binding.rg3.isChecked = false
                binding.rg4.isChecked = false
                setImageButtonPlay()
                myTimer = null
            }
        }


    }

    fun fullScreen(view: VideoView) {
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

    fun downLoadSong(linkSong: String) {

        model.linkSong(linkSong,
            {
                Utils.downloadFileFromInternet(
                    it.link, context = this
                ) { link, path, name ->
                    run {
                        if (isDestroyView) {
                            return@run
                        }
                        Toast.makeText(this, "Finish download: $name", Toast.LENGTH_SHORT)
                            .show()
                        model.loadAllMusicOffline(this)
                        getHomeFragment()?.resetTotalNumberSong()
                        getCountMusic()
                    }
                }
                if (it.link != null) {
                    return@linkSong it.link
                } else {
                    "Error"
                }

            },
            {

            }
        )
    }

    override fun onDestroy() {
        isDestroyView = true
        super.onDestroy()
    }

    private fun isCheckInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}