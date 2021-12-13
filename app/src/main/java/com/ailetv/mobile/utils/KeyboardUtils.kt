package com.ailetv.mobile.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


fun TextInputLayout.showSoftKeyboard() {
    postDelayed({
        editText?.let {
            it.requestFocus()
            it.setSelection(it.text.length)

            val imm =
                context?.let {
                    ContextCompat.getSystemService(
                        context,
                        InputMethodManager::class.java
                    )
                }
            imm?.showSoftInput(it, 0)
        }
    }, 50)
}

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

fun Activity.hideSoftKeyboard() {
    if (KeyboardVisibilityEvent.isKeyboardVisible(this))
        window.decorView.hideSoftKeyboard()
}

fun View?.hideSoftKeyboard() {
    this?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(it.context, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}