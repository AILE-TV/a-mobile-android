package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.model.request.PaymentCreateRequest
import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.PaymentApiService
import com.ailetv.mobile.ui.base.BaseRepo

class PaymentRepo(private val api: PaymentApiService) : BaseRepo() {
    suspend fun create(request: PaymentCreateRequest) = callApi {
        val response = api.create(request)
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun getStatus(paymentId: Int?) = callApi {
        val response = api.getStatus(paymentId)
        ResponseHandler.handleSuccess(response, response.data)
    }
}