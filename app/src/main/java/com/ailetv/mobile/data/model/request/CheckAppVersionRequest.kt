package com.ailetv.mobile.data.model.request

import com.ailetv.mobile.BuildConfig
import com.google.gson.annotations.SerializedName

data class CheckAppVersionRequest(
    @SerializedName("version")
    val version: String = BuildConfig.VERSION_NAME,
    @SerializedName("osName")
    val osName: String = "Android"
)