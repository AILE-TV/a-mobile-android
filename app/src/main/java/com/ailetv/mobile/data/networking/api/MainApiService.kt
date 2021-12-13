package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.data.model.request.ContractRequest
import com.ailetv.mobile.data.model.response.BaseResponse
import com.ailetv.mobile.data.model.response.BonusBalanceResponse
import com.ailetv.mobile.data.model.response.ContractListResponse
import com.ailetv.mobile.data.model.response.CustomerListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @GET("main/getBonusBalance")
    suspend fun getBonusBalance(): BonusBalanceResponse

    @GET("main/getCustomerList")
    suspend fun getCustomerList(): CustomerListResponse

    @POST("main/getContractList")
    suspend fun getContractList(@Body request: ContractRequest): ContractListResponse

    @GET("main/payBonus/{id}")
    suspend fun payBonus(@Path("id") id: String?): BaseResponse
}
