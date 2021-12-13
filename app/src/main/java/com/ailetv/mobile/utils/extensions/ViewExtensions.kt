package com.ailetv.mobile.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


inline fun <reified T> RecyclerView.setAdapter(adapter: ListAdapter<*, *>): T {
    if (this.adapter == null)
        this.adapter = adapter

    return adapter as T
}

fun View.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}


