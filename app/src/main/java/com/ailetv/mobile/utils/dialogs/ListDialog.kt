package com.ailetv.mobile.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleObserver
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.DialogRecyclerViewBinding

class ListDialog(private val context: Context?) : LifecycleObserver {
    private lateinit var binding: DialogRecyclerViewBinding
    private var dialog: Dialog? = null

    var onItemClick: ((String, Int) -> Unit?)? = null

    init {
        context?.let { createDialog(it) }
    }

    fun show(list: List<String>?) {
        val adapter = ChooseItemAdapter()
        adapter.submitList(list)
        binding.recyclerView.adapter = adapter

        adapter.onItemClick = {text,position->
            onItemClick?.invoke(text,position)
        }

        dialog?.show()
    }

    private fun createDialog(context: Context) {
        binding = DialogRecyclerViewBinding.inflate(LayoutInflater.from(context))

        dialog = Dialog(context, R.style.AppThemeDialog).apply {
            setContentView(binding.root)

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes?.windowAnimations = R.style.AppThemeDialog
            }
        }
    }

    fun dismiss(){
        dialog?.dismiss()
    }
}