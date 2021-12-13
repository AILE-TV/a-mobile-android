package com.ailetv.mobile.data.enums

enum class PaymentStatus(val status: Int) {
    SUCCESSFUL(1),
    UN_SUCCESSFUL(0);

    companion object {
        fun byStatus(status: Int?) = values().find { it.status == status } ?: UN_SUCCESSFUL
    }
}