package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.data.model.request.*
import com.ailetv.mobile.data.model.response.BaseResponse
import com.ailetv.mobile.data.model.response.CheckAppVersionResponse
import com.ailetv.mobile.data.model.response.CheckOtpResponse
import com.ailetv.mobile.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/authorise")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/checkOTP")
    suspend fun checkOtp(@Body request: CheckOtpRequest): CheckOtpResponse

    @POST("auth/reSendOtp")
    suspend fun resendOtp(@Body request: ResendOtpRequest): BaseResponse

    @POST("auth/firebaseToken")
    suspend fun firebaseToken(@Body request: FirebaseTokenRequest): BaseResponse

    @POST("auth/checkVersion")
    suspend fun checkAppVersion(@Body request: CheckAppVersionRequest): CheckAppVersionResponse
}
