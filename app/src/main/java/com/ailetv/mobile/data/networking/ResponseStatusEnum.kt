package com.ailetv.mobile.data.networking

enum class ResponseStatusEnum(private val status: String?) {
    OK("Ok"),
    FAILED("Failed"),
    NULL_DATA("NullData");

    companion object {
        fun getStatus(status: String?) = values().find { it.status == status } ?: FAILED
    }
}