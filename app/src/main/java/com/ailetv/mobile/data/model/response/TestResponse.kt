package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("data")
    val list : List<com.ailetv.mobile.data.model.resource.TestModel>?,
): com.ailetv.mobile.data.model.response.BaseResponse()
