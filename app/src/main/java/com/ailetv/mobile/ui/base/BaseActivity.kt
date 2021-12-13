package com.ailetv.mobile.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.ailetv.mobile.utils.hideSoftKeyboard

abstract class BaseActivity : AppCompatActivity() {
    override fun onSupportNavigateUp(): Boolean {
        hideSoftKeyboard()

        onBackPressed()
        return true
    }
}