package com.ailetv.mobile.data.model.response
import com.google.gson.annotations.SerializedName


data class PaymentStatusResponse(
    @SerializedName("data")
    val data: PaymentStatusData,
):BaseResponse()

data class PaymentStatusData(
    @SerializedName("status")
    val status: Int?
)