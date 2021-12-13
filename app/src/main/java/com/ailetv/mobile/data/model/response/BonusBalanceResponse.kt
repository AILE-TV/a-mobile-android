package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.model.resource.BonusBalanceModel

data class BonusBalanceResponse(
    @SerializedName("data")
    val data: BonusBalanceModel
) : BaseResponse()