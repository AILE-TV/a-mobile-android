package com.ailetv.mobile.data.model.request

import com.google.gson.annotations.SerializedName


data class ContractRequest(
    @SerializedName("customerId")
    val customerId: Int?,
) : BaseRequest()