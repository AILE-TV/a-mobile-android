package com.ailetv.mobile.data.model.request

import com.google.gson.annotations.SerializedName


data class ResendOtpRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("userId")
    val userId: Int?
) : BaseRequest()