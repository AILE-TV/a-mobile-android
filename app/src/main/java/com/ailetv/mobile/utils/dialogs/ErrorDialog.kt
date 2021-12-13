package com.ailetv.mobile.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ailetv.mobile.R
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.databinding.DialogErrorBinding
import isNetworkAvailable

class ErrorDialog(lifecycle: Lifecycle? = null) : LifecycleObserver {
    private lateinit var binding: DialogErrorBinding

    private var model: ErrorDialogModel? = null
    private var dialog: Dialog? = null

    init {
        lifecycle?.addObserver(this)
    }

    fun createDialog(context: Context, layoutInflater: LayoutInflater): ErrorDialog {
        binding = DialogErrorBinding.inflate(layoutInflater)

        dialog = Dialog(context, R.style.AppThemeDialog).apply {
            setContentView(binding.root)
            setOnCancelListener {
                model?.showDialog = false
            }
            setOnDismissListener {
                model?.showDialog = false
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes?.windowAnimations = R.style.AppThemeDialog
            }
        }
        return this
    }

    fun show(context: Context?, model: ErrorDialogModel?) {
        if (!context.isNetworkAvailable()) {
            model?.message = context?.getString(R.string.no_internet)

            if (model?.showNoInternetDialog == false)
                return
        }

        this.model = model
        binding.model = model

        if (model?.positiveButton?.onClickListener == null)
            model?.positiveButton?.onClickListener = View.OnClickListener { forceDismiss() }

        if (model?.negativeButton?.onClickListener == null)
            model?.negativeButton?.onClickListener = View.OnClickListener { forceDismiss() }

        dialog?.setCancelable(model?.cancelable == true)

        show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun show() {
        if (model?.showDialog == true && dialog?.isShowing == false)
            dialog?.show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun dismiss() {
        if (model?.cancelable == true && dialog?.isShowing == true)
            dialog?.dismiss()
    }

    private fun forceDismiss() {
        dialog?.dismiss()
    }
}