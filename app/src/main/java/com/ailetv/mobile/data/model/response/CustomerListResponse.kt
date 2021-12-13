package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.model.resource.CustomerModel

data class CustomerListResponse(
    @SerializedName("data")
    val data: CustomerData,
) : BaseResponse()

data class CustomerData(
    @SerializedName("customers")
    val list: List<CustomerModel>
)
