package com.ailetv.mobile.data.model.response

import com.google.gson.annotations.SerializedName
import com.ailetv.mobile.data.enums.ContractTypeEnum

data class ContractListResponse(
    @SerializedName("data")
    val data: ContractDataBean,
) : BaseResponse()

data class ContractDataBean(
    @SerializedName("contractList")
    val list: List<ContractData>
)

data class ContractData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("contractId")
    val contractId: String?,
    @SerializedName("contractType")
    val contractType: ContractTypeEnum?,
    @SerializedName("finalDate")
    val finalDate: String?,
    @SerializedName("tariff")
    val tariff: String?,
    @SerializedName("balance")
    val balance: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("packet")
    val packet: String?
)
