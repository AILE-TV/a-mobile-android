package com.ailetv.mobile.data

import androidx.databinding.ObservableField
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.model.resource.NotificationPOJO
import com.ailetv.mobile.data.model.resource.ServicePOJO
import com.ailetv.mobile.data.model.response.ContractData
import com.ailetv.mobile.data.model.response.NotificationData
import com.ailetv.mobile.data.model.response.ServicesData
import com.ailetv.mobile.utils.extensions.getFormattedDate

fun List<NotificationData>.asPojoList() = map { it.asPojo() }

fun NotificationData.asPojo() = NotificationPOJO(
    id = messageId,
    title = messageText,
    date = dateTime.getFormattedDate("dd.MM.yyyy"),
    isSeen = ObservableField(messageStatus == 1)
)

@JvmName("asPojoListContractData")
fun List<ContractData>.asPojoList() = map { it.asPojo() }

fun ContractData.asPojo() = ContractPOJO(
    id = id,
    contractId = contractId,
    finalDate = finalDate.getFormattedDate("d/MM/yyyy", currentFormat = "dd.MM.yyyy"),
    tariff = tariff,
    balance = balance,
    status = status,
    contractType = contractType,
    isActive = ObservableField(false),
    packet = packet,
)

@JvmName("asPojoListServicesData")
fun List<ServicesData>.asPojoList() = map { it.asPojo() }

fun ServicesData.asPojo() = ServicePOJO(
    customerId = customerId,
    serviceId = serviceId,
    dateTime = dateTime.getFormattedDate("d/MM/yyyy"),
    contractId = contractId,
    contractType = contractType,
    serviceDate = serviceDate.getFormattedDate("d/MM/yyyy"),
    serviceType = serviceType,
    serviceText = serviceText,
    status = status,
)