package com.ailetv.mobile.data.model.request

import com.ailetv.mobile.data.enums.ContractTypeEnum
import com.google.gson.annotations.SerializedName


data class PaymentCreateRequest(
    @SerializedName("contractType")
    val contractType: ContractTypeEnum?,
    @SerializedName("contractId")
    val contractId: String?,
    @SerializedName("amount")
    val amount: Int?
) : BaseRequest()