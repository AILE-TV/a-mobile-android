package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.CampaignsApiService
import com.ailetv.mobile.ui.base.BaseRepo

class CampaignsRepo(private val api: CampaignsApiService) : BaseRepo() {
    suspend fun getCampaigns() = callApi {
        val response = api.campaigns()
        ResponseHandler.handleSuccess(response, response.data)
    }
}