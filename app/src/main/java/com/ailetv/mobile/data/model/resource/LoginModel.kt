package com.ailetv.mobile.data.model.resource

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("userId")
    val userId: Int?
)