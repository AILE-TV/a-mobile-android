package com.ailetv.mobile.data.networking.api

import retrofit2.http.GET
import retrofit2.http.Path
import com.ailetv.mobile.data.model.response.ServicesResponse

interface ServicesApiService {
    @GET("serviceList/{customerId}")
    suspend fun getServices(@Path("customerId") customerId: Int?): ServicesResponse
}
