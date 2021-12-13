package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.data.model.response.BaseResponse
import com.ailetv.mobile.data.model.response.BonusHintResponse
import retrofit2.http.GET

interface OthersApiService {
    @GET("bonusHint")
    suspend fun getBonusHint(): BonusHintResponse

    @GET("version/android_version")
    suspend fun getAndroidVersion(): BaseResponse
}
