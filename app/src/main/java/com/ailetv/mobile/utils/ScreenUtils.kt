package com.ailetv.mobile.utils

import android.content.Context
import android.content.res.Resources


fun Context?.getScreenWidth() = this?.resources?.displayMetrics?.widthPixels ?: 0

fun Context?.getStatusBarHeight(): Int {

    return this?.let {
        val myResources: Resources = it.resources
        val idStatusBarHeight =
            myResources.getIdentifier("status_bar_height", "dimen", "android")
        if (idStatusBarHeight > 0) {
            it.resources.getDimensionPixelSize(idStatusBarHeight)
        } else 0
    } ?: 0

}