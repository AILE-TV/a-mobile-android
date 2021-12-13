package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.model.resource.LoginModel

data class LoginResponse(
    @SerializedName("data")
    val data: LoginModel
) : BaseResponse()