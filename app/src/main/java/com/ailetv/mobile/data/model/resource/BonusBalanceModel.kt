package com.ailetv.mobile.data.model.resource

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BonusBalanceModel(
    @SerializedName("bonusBalance")
    val bonusBalance: Double,
    @SerializedName("payBonus")
    val payBonus: Boolean?
) : Parcelable