package com.ailetv.mobile.data.model.resource

import com.ailetv.mobile.data.enums.PushNotificationType
import com.google.gson.annotations.SerializedName

data class PushNotificationModel(
    @SerializedName("type")
    private val _type: PushNotificationType?
) {
    val type get() = _type ?: PushNotificationType.UNKNOWN
}