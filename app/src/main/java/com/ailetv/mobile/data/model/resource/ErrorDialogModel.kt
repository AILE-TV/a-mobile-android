package com.ailetv.mobile.data.model.resource

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class ErrorDialogModel(
    val title: String? = "",
    var message: String? = "",
    var cancelable: Boolean = true,
    var showDialog: Boolean = true,
    var showNoInternetDialog: Boolean = true,
    var positiveButton: ButtonModel = ButtonModel(),
    var negativeButton: ButtonModel = ButtonModel(text = 0),
)