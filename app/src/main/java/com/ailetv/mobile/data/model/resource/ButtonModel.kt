package com.ailetv.mobile.data.model.resource

import android.view.View
import androidx.annotation.StringRes
import com.ailetv.mobile.R

data class ButtonModel(
    @StringRes var text: Int = R.string.ok,
    var onClickListener: View.OnClickListener? = null
)