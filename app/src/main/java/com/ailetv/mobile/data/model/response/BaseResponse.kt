package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.networking.ResponseStatusEnum

open class BaseResponse(
    @SerializedName("success")
    val success: Boolean? = false,
    @SerializedName("message")
    val message: String? = ""
) {
    fun getStatus(): ResponseStatusEnum {
        return if (success == true) ResponseStatusEnum.OK else ResponseStatusEnum.FAILED
    }
}
