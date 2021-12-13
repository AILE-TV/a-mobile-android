package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("data")
    val data: List<NotificationData>,
) : BaseResponse()

data class NotificationData(
    @SerializedName("messageId")
    val messageId: Int?,
    @SerializedName("dateTime")
    val dateTime: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("messageType")
    val messageType: String?,
    @SerializedName("messageText")
    val messageText: String?,
    @SerializedName("messageStatus")
    val messageStatus: Int?,
    @SerializedName("readDateTime")
    val readDateTime: String?,
    @SerializedName("deleteDateTime")
    val deleteDateTime: String?
)