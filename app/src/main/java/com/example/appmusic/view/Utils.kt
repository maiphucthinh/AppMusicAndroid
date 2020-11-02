package com.example.appmusic.view

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.appmusic.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.net.URL


object Utils {
    @BindingAdapter("setText")
    @JvmStatic
    fun setText(tv: TextView, content: String?) {
        tv.setText(content)
    }

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(im: ImageView, linkImage: String?) {
        if (linkImage == null || linkImage.equals("")) {
            im.setImageResource(R.drawable.mylove1)
        }
        Glide.with(im)
            .load(linkImage)
            .error(R.drawable.mylove1)
            .into(im)

    }

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(
        im: ImageView, image: Int
    ) {
        im.setImageResource(image)
    }

    @BindingAdapter("setColor")
    @JvmStatic
    fun setColor(
        im: TextView, color: String
    ) {
        when (color) {
            "01" -> im.setTextColor(Color.parseColor("#FF9800"))
            "02" -> im.setTextColor(Color.parseColor("#8BC34A"))
            "03" -> im.setTextColor(Color.parseColor("#2196F3"))
            "04" -> im.setTextColor(Color.parseColor("#B71C1C"))
            else -> im.setTextColor(Color.parseColor("#000000"))


        }
    }

    private fun saveIntoDatabase(context:Context?, title:String, path:String){
        if (context == null){
            return
        }
        var name = title
        val values = ContentValues()
        if ( name.endsWith(".mp3")){
            name = name.substring(0, name.length-4)
        }
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true)
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name   )
        values.put(MediaStore.Audio.Media.TITLE, name)
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/*")
        values.put(MediaStore.Audio.Media.DATA, File(path).absolutePath)
        context.contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
    }

    fun downloadFileFromInternet(
        link: String,
        context:Context? = null,
        finishDownload: ((link: String, path: String, name: String) -> Unit)? = null
    ) {
        Observable.just(link)
            .observeOn(Schedulers.newThread())
            .subscribe {
                val url = URL(it)
                val conn = url.openConnection()
                val contentLength = conn.contentLength
                var name = conn.url.path.substring(conn.url.path.lastIndexOf('/') + 1)
                var pathSave = Environment.getExternalStorageDirectory().path +
                        File.separator + Environment.DIRECTORY_DOWNLOADS +
                        File.separator + "AppMusic"
                File(pathSave).mkdir()
                name = name.replace("%20", " ")
                pathSave = pathSave + File.separator + name

                val input = conn.getInputStream()
                val out = FileOutputStream(pathSave)
                val b = ByteArray(1024)
                var le = input.read(b)
                var currentLe = le
                while (le >= 0) {
                    out.write(b, 0, le)
                    currentLe += le
                    Log.d(
                        "Utils",
                        "downloadFileFromInternet percent: " + (currentLe * 100 / contentLength).toString()
                    )
                    le = input.read(b)
                }
                input.close()
                out.close()

                saveIntoDatabase(context, name, pathSave)


                if (finishDownload != null) {
                    Observable.just(arrayOf(link, pathSave, name))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            finishDownload(it[0], it[1], it[2])
                        }
                }

            }

    }

    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}