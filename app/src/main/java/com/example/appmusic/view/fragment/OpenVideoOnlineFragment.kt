package com.example.appmusic.view.fragment

import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.appmusic.R
import com.example.appmusic.databinding.OpenVideoFragmentBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.viewpager.VideoAdapter
import java.text.SimpleDateFormat
import java.util.concurrent.Executors


class OpenVideoOnlineFragment : Fragment(), View.OnClickListener, MediaPlayer.OnCompletionListener,
    CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private lateinit var binding: OpenVideoFragmentBinding
    private lateinit var listVideo: ListVideoChildFragment
    private var asyPlay: MyAsyn? = null
    private var isCheckLoading = true
    private var async: MyAsyncTank? = null
    private var index = 0
    private var isCheckAuto = false
    private var format: SimpleDateFormat? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        format = SimpleDateFormat("mm:ss")
        binding = OpenVideoFragmentBinding.inflate(
            inflater
        )
        binding.vpg.adapter = VideoAdapter(childFragmentManager)
        binding.tab.setupWithViewPager(
            binding.vpg
        )
        isCheckAuto = binding.auto.isChecked
        listVideo = ListVideoChildFragment()
        binding.control.visibility = View.GONE
        binding.playOrPause.setOnClickListener(this)
        binding.video.setOnClickListener(this)
        binding.sb.setOnClickListener(this)
        binding.next.setOnClickListener(this)
        binding.previous.setOnClickListener(this)
        binding.video.setOnCompletionListener(this)
        binding.zoom.setOnClickListener(this)
        binding.auto.setOnCheckedChangeListener(this)
        index = arguments!!.getInt("POSITION", 1)
        binding.sb.setOnSeekBarChangeListener(this)
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().isLoading.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.control.visibility = View.GONE
                binding.prg.visibility = View.VISIBLE
            } else {
                binding.prg.visibility = View.GONE
                binding.control.visibility = View.VISIBLE
                initAsync(true)
            }
        })

        (activity as MainActivity).getModel().linkVideoOnline.observe(this, Observer {
            val linKVideo = it.link
            initVideo(linKVideo)
            binding.nameSong.setText(it.songName)
            binding.artist.setText(it.artistName)
        })
    }


    private fun initVideo(linkVideo: String) {
        binding.video.setVideoURI(
            Uri.parse(
                linkVideo
            )
        )
        binding.sb.progress = 0
        startAsyn()
        binding.playOrPause.setImageResource(
            R.drawable.baseline_pause_circle_outline_white_24dp
        )
        binding.video.requestFocus()
        binding.video.start()
    }

    fun startAsyn() {
        if (asyPlay != null) {
            asyPlay!!.isRunning = false
        }
        asyPlay = MyAsyn()
        asyPlay!!.executeOnExecutor(Executors.newFixedThreadPool(1))
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.video -> {
                binding.control.visibility = View.VISIBLE
                initAsync(true)
            }
            R.id.play_or_pause -> {
                if (binding.video.isPlaying) {
                    binding.video.pause()
                    binding.playOrPause.setImageResource(
                        R.drawable.baseline_play_circle_outline_white_24dp
                    )
                } else {
                    binding.video.start()
                    binding.playOrPause.setImageResource(
                        R.drawable.baseline_pause_circle_outline_white_24dp
                    )
                }

            }
            R.id.sb -> initAsync(true)
            R.id.next -> {
                if (index == (activity as MainActivity).getModel().listVideo.value!!.size - 1) {
                    index = 0
                }

                index += 1
                val data = (activity as MainActivity).getModel().listVideo.value!![index]
                (activity as MainActivity).getModel().getLinkVideo(
                    data.linkMusic
                )
                asyPlay?.isRunning = false
                asyPlay?.cancel(true)
                asyPlay = null

            }
            R.id.previous -> {
                if (index == 0) {
                    index = (activity as MainActivity).getModel().listVideo.value!!.size - 1
                }
                index -= 1
                val data = (activity as MainActivity).getModel().listVideo.value!![index]
                (activity as MainActivity).getModel().getLinkVideo(
                    data.linkMusic
                )
                asyPlay?.isRunning = false
                asyPlay?.cancel(true)
                asyPlay = null

            }
            R.id.zoom -> {
                (activity as MainActivity).fullScreen(binding.video)
                binding.video.layoutParams.height = 6000
                binding.video.layoutParams.width = 4000
            }
        }
    }


    inner class MyAsyn : AsyncTask<Void, Int?, Void?>() {
        var isRunning = true
        override fun doInBackground(vararg params: Void?): Void? {
            while (isRunning) {
                publishProgress(binding.video.currentPosition)
                SystemClock.sleep(500)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            binding.totalTime.text = format!!.format(binding.video.duration)
            binding.currentTime.text = (format!!.format(values[0]))
            binding.sb.progress = values[0]!! * 100 / binding.video.duration

        }
    }

    inner class MyAsyncTank : AsyncTask<Int, Int, Boolean> {
        var isRuning: Boolean

        constructor(isRuning: Boolean) {
            this.isRuning = isRuning
        }


        override fun doInBackground(vararg params: Int?): Boolean {
            for (i in 0..params[0]!!) {
                if (!isRuning) {
                    return false
                }
                SystemClock.sleep(1000)
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if (result == true) {
                binding.control.visibility = View.GONE
            }
        }

    }

    private fun initAsync(isRunning: Boolean) {
        async?.isRuning = false
        async = MyAsyncTank(isRunning)
        async?.execute(5)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        binding.playOrPause.setImageResource(
            R.drawable.baseline_play_circle_outline_white_24dp
        )
        if (isCheckAuto) {
            getListVideo().nextVideo()
            binding.playOrPause.setImageResource(
                R.drawable.baseline_pause_circle_outline_white_24dp
            )
        }
    }

    override fun onDestroy() {
        asyPlay?.isRunning = false
        asyPlay?.cancel(true)
        asyPlay = null
        binding.video.requestFocus()
        binding.video.stopPlayback()
        super.onDestroy()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        isCheckAuto = isChecked
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            binding.video.seekTo(
                progress * binding.video.duration / 100
            )
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        asyPlay?.isRunning = false
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        startAsyn()
    }
    private fun getListVideo():ListVideoChildFragment{
       return (binding.vpg.adapter as VideoAdapter).lisVideo
    }
}