package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.model.request.*
import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.AuthApiService
import com.ailetv.mobile.ui.base.BaseRepo

class AuthRepo(private val api: AuthApiService) : BaseRepo() {
    suspend fun login(request: LoginRequest) = callApi {
        val response = api.login(request)
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun checkOtp(request: CheckOtpRequest) = callApi {
        val response = api.checkOtp(request)
        ResponseHandler.handleSuccess(response, response.data)
    }

    suspend fun resendOtp(request: ResendOtpRequest) = callApi {
        val response = api.resendOtp(request)
        ResponseHandler.handleSuccess(response, response)
    }

    suspend fun firebaseToken(request: FirebaseTokenRequest) = callApi {
        val response = api.firebaseToken(request)
        ResponseHandler.handleSuccess(response, response)
    }

    suspend fun checkAppVersion(request: CheckAppVersionRequest = CheckAppVersionRequest()) =
        callApi {
            val response = api.checkAppVersion(request)
            ResponseHandler.handleSuccess(response, response.data?.forceUpdate)
        }
}