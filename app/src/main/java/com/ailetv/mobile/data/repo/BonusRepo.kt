package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.MainApiService
import com.ailetv.mobile.data.networking.api.OthersApiService
import com.ailetv.mobile.ui.base.BaseRepo

class BonusRepo(
    private val othersApi: OthersApiService,
    private val mainApi: MainApiService
) : BaseRepo() {
    suspend fun getBonusBalance() = callApi {
        val response = mainApi.getBonusBalance()
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun getBonusHint() = callApi {
        val response = othersApi.getBonusHint()
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun payBonus(id: String?) = callApi {
        val response = mainApi.payBonus(id)
        ResponseHandler.handleSuccess(response, response)
    }
}