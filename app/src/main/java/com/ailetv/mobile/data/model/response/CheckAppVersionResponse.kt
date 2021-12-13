package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName

data class CheckAppVersionResponse(
    @SerializedName("data")
    val data: CheckAppVersionData?
) : BaseResponse()

data class CheckAppVersionData(
    @SerializedName("forceUpdate")
    val forceUpdate: Boolean?
)
