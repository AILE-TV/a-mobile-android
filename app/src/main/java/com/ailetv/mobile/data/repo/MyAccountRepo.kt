package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.MyAccountApiService
import com.ailetv.mobile.ui.base.BaseRepo

class MyAccountRepo(private val api: MyAccountApiService) : BaseRepo() {
    suspend fun logout() = callApi {
        val response = api.logout()
        ResponseHandler.handleSuccess(response, response)
    }
}