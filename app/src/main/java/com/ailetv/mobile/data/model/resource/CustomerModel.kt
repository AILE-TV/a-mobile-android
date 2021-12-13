package com.ailetv.mobile.data.model.resource

import com.google.gson.annotations.SerializedName

data class CustomerModel(
    @SerializedName("customerId")
    val customerId: Int?,
    @SerializedName("customerName")
    val customerName: String?,
    @SerializedName("address")
    val address: String?
)