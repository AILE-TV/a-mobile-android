package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.data.model.response.CampaignsResponse
import retrofit2.http.GET

interface CampaignsApiService {
    @GET("compaigns")
    suspend fun campaigns(): CampaignsResponse
}
