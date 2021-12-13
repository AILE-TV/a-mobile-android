package com.ailetv.mobile.utils

import android.animation.ValueAnimator
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.drawToBitmap
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.show() {
    if (visibility == View.VISIBLE) return

    try {
        val parent = parent as ViewGroup
        if (!isLaidOut) {
            measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
            )
            layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
        }

        val drawable = BitmapDrawable(context.resources, drawToBitmap())
        drawable.setBounds(left, parent.height, right, parent.height + height)
        parent.overlay.add(drawable)
        ValueAnimator.ofInt(parent.height, top).apply {
            startDelay = 100L
            duration = 200L

            addUpdateListener {
                val newTop = it.animatedValue as Int
                drawable.setBounds(left, newTop, right, newTop + height)
            }
            doOnEnd {
                parent.overlay.remove(drawable)
                visibility = View.VISIBLE
            }
            start()
        }
    } catch (e: Exception) {

    }
}

fun BottomNavigationView.hide() {
    if (visibility == View.GONE) return

    if (top <= 0) {
        visibility = View.GONE
        return
    }

    try {
        val drawable = BitmapDrawable(context.resources, drawToBitmap())
        val parent = parent as ViewGroup
        drawable.setBounds(left, top, right, bottom)
        parent.overlay.add(drawable)
        visibility = View.GONE
        ValueAnimator.ofInt(top, parent.height).apply {
            startDelay = 100L
            duration = 200L

            addUpdateListener {
                val newTop = it.animatedValue as Int
                drawable.setBounds(left, newTop, right, newTop + height)
            }
            doOnEnd {
                parent.overlay.remove(drawable)
            }
            start()
        }
    } catch (e: Exception) {

    }
}