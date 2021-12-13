package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.data.model.request.PaymentCreateRequest
import com.ailetv.mobile.data.model.response.PaymentCreateResponse
import com.ailetv.mobile.data.model.response.PaymentStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {
    @POST("payment/create")
    suspend fun create(@Body request: PaymentCreateRequest): PaymentCreateResponse

    @GET("payment/getStatus/{paymentId}")
    suspend fun getStatus(@Path("paymentId") paymentId: Int?): PaymentStatusResponse
}
