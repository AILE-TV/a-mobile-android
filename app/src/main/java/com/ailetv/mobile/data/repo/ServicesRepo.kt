package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.asPojoList
import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.ServicesApiService
import com.ailetv.mobile.ui.base.BaseRepo

class ServicesRepo(private val api: ServicesApiService) : BaseRepo() {
    suspend fun getServices(customerId: Int?) = callApi {
        val response = api.getServices(customerId)
        ResponseHandler.handleSuccess(response, response.data.asPojoList())
    }
}