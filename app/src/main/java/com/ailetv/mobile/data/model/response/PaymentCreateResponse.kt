package com.ailetv.mobile.data.model.response
import com.google.gson.annotations.SerializedName


data class PaymentCreateResponse(
    @SerializedName("data")
    val data: PaymentCreateData,
):BaseResponse()

data class PaymentCreateData(
    @SerializedName("paymentId")
    val paymentId: Int?,
    @SerializedName("redirectUrl")
    val redirectUrl: String?,
    @SerializedName("postData")
    val postData: String?
)