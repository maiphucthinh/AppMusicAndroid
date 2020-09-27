package com.example.appmusic.view

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.appmusic.R

object Utils {
    @BindingAdapter("setText")
    @JvmStatic
    fun setText(tv: TextView, content: String?) {
        tv.setText(content)
    }

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(im: ImageView, linkImage: String) {
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

}