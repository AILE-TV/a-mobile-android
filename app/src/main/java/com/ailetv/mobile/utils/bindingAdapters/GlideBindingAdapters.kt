package com.ailetv.mobile.utils.bindingAdapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber


object GlideBindingAdapters {

    @JvmStatic
    @BindingAdapter("bind:glideLoadImg")
    fun ImageView.glideLoadImg(url: String?) {
        this.context?.let {
            Glide.with(it)
                .load(url)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?,
                        target: Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        Timber.e(e.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(this)
        }
    }
}