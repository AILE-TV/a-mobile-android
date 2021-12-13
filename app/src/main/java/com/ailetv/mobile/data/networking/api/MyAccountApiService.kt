package com.ailetv.mobile.data.networking.api

import retrofit2.http.GET
import com.ailetv.mobile.data.model.response.BaseResponse

interface MyAccountApiService {
    @GET("auth/logout")
    suspend fun logout(): BaseResponse
}
