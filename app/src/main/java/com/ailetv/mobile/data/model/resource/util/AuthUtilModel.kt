package com.ailetv.mobile.data.model.resource.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthUtilModel(
    val userId: Int? = 0,
    val phoneNumber: String? = "",
    val phoneNumberMasked: String? = "",
) : Parcelable