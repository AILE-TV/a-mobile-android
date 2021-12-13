package com.ailetv.mobile.data.model.resource

import com.ailetv.mobile.data.enums.ContractTypeEnum

data class ServicePOJO(
    val customerId: Int?,
    val serviceId: Int?,
    val dateTime: String?,
    val contractId: Int?,
    val contractType: ContractTypeEnum?,
    val serviceDate: String?,
    val serviceType: String?,
    val serviceText: String?,
    val status: String?
)