package com.ailetv.mobile.data.enums

import com.google.gson.annotations.SerializedName

enum class PushNotificationType() {
    @SerializedName("push")
    PUSH,
    UNKNOWN;
}