package com.ailetv.mobile.utils.bindingAdapters

import android.graphics.Typeface
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ailetv.mobile.R
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.utils.OnSingleClickListener
import com.ailetv.mobile.utils.extensions.toDoubleOrZero
import com.ailetv.mobile.utils.getStatusBarHeight
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("bind:isVisible")
fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("bind:isVisibleElseGone")
fun View.setVisibleElseGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:onSingleClick")
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

@BindingAdapter("bind:circleProgressIndicatorColor")
fun CircularProgressIndicator.setCircleProgressIndicatorColor(color: Int) {
    setIndicatorColor(color)
}

@BindingAdapter("bind:enabled")
fun View.setText(enabled: Boolean?) {
    this.isEnabled = enabled == true
}

@BindingAdapter("bind:textRes")
fun TextView.setText(resId: Int) {
    if (resId > 0)
        this.setText(resId)
}

@BindingAdapter("bind:textInt")
fun TextView.setTextInt(i: Int?) {
    this.text = i.toString()
}

@BindingAdapter("bind:isBold")
fun TextView.setBold(isBold: Boolean) {
    if (isBold)
        setTypeface(null, Typeface.BOLD)
    else
        setTypeface(null, Typeface.NORMAL)
}

@BindingAdapter("bind:srcRes")
fun ImageView.setBold(resId: Int?) {
    resId?.let {
        setImageResource(resId)
    }
}

@BindingAdapter("bind:fitsSystemWindowsToolbar")
fun MaterialToolbar.fitsSystemWindowsToolbar(boolean: Boolean?) {
    setPadding(this.marginLeft, context.getStatusBarHeight(), this.marginLeft, this.marginBottom)
}

@BindingAdapter("bind:checkMotionLayoutState")
fun SwipeRefreshLayout.checkMotionLayoutState(uiState: UiState?) {
    findViewById<MotionLayout>(R.id.motionLayout)?.let {
        isEnabled = it.progress == 0f

        it.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                isEnabled = it.progress == 0f

                if (!isRefreshing)
                    isRefreshing = false
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                isEnabled = it.progress == 0f
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
        })
    }
}

@BindingAdapter("bind:selectionActionModeDisable")
fun EditText.selectionActionModeDisable(boolean: Boolean?) {
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?) = false
        override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?) = false
        override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?) = false
        override fun onDestroyActionMode(actionMode: ActionMode) {}
    }
}

@BindingAdapter("bind:paymentMaxAmount", requireAll = true)
fun EditText.paymentAmount(maxAmount: Double) {
    doAfterTextChanged {
        text.toString().run {
            val currentAmount = toDoubleOrZero()

            // 101 > 100
            if (currentAmount > maxAmount) {
                if (maxAmount.toInt() < maxAmount) {    // 100.1 > 100 -> 100.1
                    setText(maxAmount.toString())
                } else                                  // 100.0 == 100 -> 100
                    setText(maxAmount.toInt().toString())

                setSelection(length())
            }

            // .0 -> 0.
            if (startsWith(".")) {
                setText(replace(".", "0."))
                setSelection(length())
            }

            // 100.000 -> 100.00
            if (lastIndexOf(".") > 0 && lastIndexOf(".") < length() - 3) {
                setText(substring(0, length() - 1))
                setSelection(length())
            }

            // 100.000 -> 100.00
            if (length() >= 2 && startsWith("0") && substring(0, 2).contains(".").not()) {
                setText(substring(1, length()))
                setSelection(length())
            }
        }
    }
}