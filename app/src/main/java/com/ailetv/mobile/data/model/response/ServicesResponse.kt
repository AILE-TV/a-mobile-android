package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.enums.ContractTypeEnum

data class ServicesResponse(
    @SerializedName("data")
    val data: List<ServicesData>,
) : BaseResponse()


data class ServicesData(
    @SerializedName("customerId")
    val customerId: Int?,
    @SerializedName("serviceId")
    val serviceId: Int?,
    @SerializedName("dateTime")
    val dateTime: String?,
    @SerializedName("contractId")
    val contractId: Int?,
    @SerializedName("contractType")
    val contractType: ContractTypeEnum?,
    @SerializedName("serviceDate")
    val serviceDate: String?,
    @SerializedName("serviceType")
    val serviceType: String?,
    @SerializedName("serviceText")
    val serviceText: String?,
    @SerializedName("status")
    val status: String?
)