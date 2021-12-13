package com.ailetv.mobile.data.model.request

import com.ailetv.mobile.utils.DeviceUtil
import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("deviceId")
    val deviceId: String? = DeviceUtil.deviceId,
    @SerializedName("deviceOS")
    val deviceOS: String = "Android",
    @SerializedName("deviceModel")
    val deviceModel: String? = DeviceUtil.getDeviceName(),
    @SerializedName("ipAddress")
    val ipAddress: String? = DeviceUtil.getIPAddress(true)
) : BaseRequest()