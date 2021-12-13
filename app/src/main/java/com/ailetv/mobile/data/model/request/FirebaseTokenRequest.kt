package com.ailetv.mobile.data.model.request

import com.google.gson.annotations.SerializedName


data class FirebaseTokenRequest(
    @SerializedName("token")
    val token: String?
) : BaseRequest()