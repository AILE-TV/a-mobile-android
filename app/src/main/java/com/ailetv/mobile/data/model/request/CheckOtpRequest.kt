package com.ailetv.mobile.data.model.request

import com.google.gson.annotations.SerializedName


data class CheckOtpRequest(
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("otp")
    val otp: Int?
) : BaseRequest()