package com.emreozcan.besinkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emreozcan.besinkitabi.R

fun ImageView.gorselIndir(url: String?,placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.drawable.ic_launcher_background)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}
fun placeholderCreate(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView,url: String?){
    view.gorselIndir(url, placeholderCreate(view.context))
}