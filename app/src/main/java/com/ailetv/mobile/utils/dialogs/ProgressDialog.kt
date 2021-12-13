package com.ailetv.mobile.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ailetv.mobile.R
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.databinding.DialogProgressBinding

class ProgressDialog(lifecycle: Lifecycle) : LifecycleObserver {
    private lateinit var binding: DialogProgressBinding

    private var uiState: UiState = UiState.SUCCESS
    private var dialog: Dialog? = null

    init {
        lifecycle.addObserver(this)
    }

    fun createDialog(context: Context, layoutInflater: LayoutInflater): ProgressDialog {
        binding = DialogProgressBinding.inflate(layoutInflater)

        dialog = Dialog(context, R.style.AppThemeDialog).apply {
            setContentView(binding.root)
            setCancelable(false)

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes?.windowAnimations = R.style.AppThemeDialog
            }
        }
        return this
    }

    fun setUiState(uiState: UiState) {
        this.uiState = uiState

        show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun show() {
        if (uiState == UiState.LOADING)
            dialog?.show()
        else
            dismiss()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun dismiss() {
        dialog?.dismiss()
    }
}