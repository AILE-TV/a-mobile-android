package com.ailetv.mobile.data.model.resource

import com.google.gson.annotations.SerializedName

data class CheckOtpModel(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("token")
    val token: String
)