package com.example.pocedex.presentation

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapters {
    @BindingAdapter("app:url")
    @JvmStatic fun loadImage(view: ImageView, url: String?) {
        try {
            Picasso.get().load(url).into(view)
        } catch (e: Exception) {
            Log.d("Image", e.message.toString())
        }
    }
}

