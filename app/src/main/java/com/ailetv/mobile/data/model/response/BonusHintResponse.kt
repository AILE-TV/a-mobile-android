package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName

data class BonusHintResponse(
    @SerializedName("data")
    val data: String?
) : BaseResponse()