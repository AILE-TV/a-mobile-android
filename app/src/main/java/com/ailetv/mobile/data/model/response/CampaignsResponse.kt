package com.ailetv.mobile.data.model.response

import com.ailetv.mobile.data.model.resource.CampaignModel
import com.google.gson.annotations.SerializedName

data class CampaignsResponse(
    @SerializedName("data")
    val data: List<CampaignModel>,
) : BaseResponse()