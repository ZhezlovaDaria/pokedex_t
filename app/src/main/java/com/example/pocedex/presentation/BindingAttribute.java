package com.example.pocedex.presentation;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class BindingAttribute {

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        try {
            Picasso.get().load(url).into(view);
        } catch (Exception e) {
            Log.d("Image", e.getMessage());
        }
    }
}
