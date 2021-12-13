package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.model.resource.CheckOtpModel

data class CheckOtpResponse(
    @SerializedName("data")
    val data: CheckOtpModel
) : BaseResponse()