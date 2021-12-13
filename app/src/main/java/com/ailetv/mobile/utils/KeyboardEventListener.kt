package com.ailetv.mobile.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class KeyboardEventListener(
    private val root: View,
    val activity: AppCompatActivity
) : LifecycleObserver, ViewTreeObserver.OnGlobalLayoutListener {

    var onKeyboardChanged: ((isOpen: Boolean, keyboardHeight: Int) -> Unit)? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onGlobalLayout() {
        if (KeyboardVisibilityEvent.isKeyboardVisible(activity)) {
            val r = Rect()
            root.getWindowVisibleDisplayFrame(r)

            val height: Int = root.height
            val diff = height - r.bottom

            onKeyboardChanged?.invoke(true, diff)
            root.setPadding(0, 0, 0, diff)
        } else {
            onKeyboardChanged?.invoke(false, 0)
            root.setPadding(0, 0, 0, 0)
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun register() {
        root.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    fun unRegister() {
        root.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }
}