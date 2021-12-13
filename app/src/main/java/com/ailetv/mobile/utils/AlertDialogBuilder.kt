package com.ailetv.mobile.utils

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleObserver
import com.ailetv.mobile.R

class AlertDialogBuilder(context: Context) : AlertDialog.Builder(context), LifecycleObserver {
    private var dialog: AlertDialog? = null

    override fun show(): AlertDialog {
        dialog = super.show()

        val window = dialog?.window
        window?.setWindowAnimations(R.style.AppThemeDialog)
        window?.decorView?.setBackgroundResource(R.color.transparent)

        window?.setLayout(
            (context.getScreenWidth() * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return dialog!!;
    }
}