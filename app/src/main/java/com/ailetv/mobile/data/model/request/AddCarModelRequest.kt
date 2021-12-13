package com.ailetv.mobile.data.model.request

import com.google.gson.annotations.SerializedName

data class AddCarModelRequest(
    @SerializedName("modelId")
    var modelId: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String,
    @SerializedName("dailyPrice")
    var dailyPrice: Int?,
    @SerializedName("minRentDays")
    var minRentDays: Int?,
    @SerializedName("maxRentDay")
    var maxRentDay: Int?,
    @SerializedName("files")
    var files: List<String> = ArrayList()
)