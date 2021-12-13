package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.asPojoList
import com.ailetv.mobile.data.model.request.ContractRequest
import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.MainApiService
import com.ailetv.mobile.ui.base.BaseRepo

class MainRepo(private val api: MainApiService) : BaseRepo() {
    suspend fun getBonusBalance() = callApi {
        val response = api.getBonusBalance()
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun getCustomerList() = callApi {
        val response = api.getCustomerList()
        ResponseHandler.handleSuccess(response, response.data.list)
    }

    suspend fun getContractList(request: ContractRequest) = callApi {
        val response = api.getContractList(request)
        ResponseHandler.handleSuccess(response, response.data.list.asPojoList())
    }
}