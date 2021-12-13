package com.ailetv.mobile.widget

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import dpToPx
import getColorAttrs
import getColorStateListByAttr
import com.ailetv.mobile.R


class TextInputLayout(context: Context, attrs: AttributeSet?) : TextInputLayout(context, attrs) {
    private var boxStrokeNormalColor = 0
    private var boxStrokeFocusedColor = 0
    private var boxStrokeErrorColor = 0
    private var errorText: String? = ""

    var onFocusChangeListener: (focuses: Boolean) -> Unit = {}

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed)
            addedEditText()
    }

    init {
        val t = context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout)

        boxStrokeNormalColor = t.getColor(
            R.styleable.TextInputLayout_boxStrokeNormalColor,
            context.getColorAttrs(R.attr.boxStrokeNormalColor)
        )
        boxStrokeFocusedColor = t.getColor(
            R.styleable.TextInputLayout_boxStrokeFocusedColor,
            context.getColorAttrs(R.attr.boxStrokeFocusedColor)
        )
        boxStrokeErrorColor = t.getColor(
            R.styleable.TextInputLayout_boxStrokeErrorColor,
            context.getColorAttrs(R.attr.boxStrokeErrorColor)
        )

        errorText = t.getString(R.styleable.TextInputLayout_errorText)

        t.recycle()
    }

    private fun addedEditText() {
        editText?.run {
            setOnFocusChangeListener { _, b ->
                checkFocus()
                onFocusChangeListener(b)
            }

            checkFocus()
        }
    }

    private fun checkFocus() {
        editText?.run {
            val hasFocus = editText?.hasFocus() == true

            isErrorEnabled = false

            val strokeColor = if (hasFocus) boxStrokeFocusedColor else boxStrokeNormalColor
            val helperTextColor =
                context.getColorStateListByAttr(if (hasFocus) R.attr.inputLayoutFocusedHelperTextColor else R.attr.inputLayoutNormalHintColor)

            setHelperTextColor(helperTextColor)

            val bg = background as GradientDrawable
            bg.setStroke(1.5f.dpToPx(), strokeColor)
            background = bg
        }
    }

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)

        error = if (enabled) errorText else ""

        if (enabled) {
            setHelperTextColor(context.getColorStateListByAttr(R.attr.inputLayoutErrorHelperTextColor))

            editText?.run {
                val bg = background as GradientDrawable
                bg.setStroke(1.5f.dpToPx(), boxStrokeErrorColor)

                background = bg
            }
        }
    }
}