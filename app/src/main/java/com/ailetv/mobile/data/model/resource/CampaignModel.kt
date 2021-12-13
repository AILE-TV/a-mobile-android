package com.ailetv.mobile.data.model.resource

import com.google.gson.annotations.SerializedName

data class CampaignModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("dateTime")
    val dateTime: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @SerializedName("redirectUrl")
    val redirectUrl: String?,
    @SerializedName("status")
    val status: Int?
)