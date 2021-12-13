package com.ailetv.mobile.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.ViewgroupKeyValueBinding

class KeyValueView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val binding by lazy {
        ViewgroupKeyValueBinding.inflate(LayoutInflater.from(context), this)
    }

    init {
        val t = context.obtainStyledAttributes(attrs, R.styleable.KeyValueView)
        setKey(t.getString(R.styleable.KeyValueView_key))
        setValue(t.getString(R.styleable.KeyValueView_value))
        setValueColor(
            t.getColor(
                R.styleable.KeyValueView_valueColor,
                ContextCompat.getColor(context, R.color.dove_gray)
            )
        )
        t.recycle()
    }

    fun setKey(text: String?) {
        binding.keyTxt.text = text
    }

    fun setValue(text: String?) {
        binding.valueTxt.text = text
    }

    fun setValueColor(color: Int) {
        binding.valueTxt.setTextColor(color)
    }
}