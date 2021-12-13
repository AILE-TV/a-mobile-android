package com.ailetv.mobile.data.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ailetv.mobile.R
import com.google.gson.annotations.SerializedName

enum class ContractTypeEnum(
    @DrawableRes val iconRes: Int,
    @DrawableRes val logoRes: Int,
    @StringRes val titleRes: Int
) {
    @SerializedName("NET")
    INTERNET(R.drawable.ic_internet, R.drawable.ic_logo_internet, R.string.internet_contract),

    @SerializedName("TV")
    TV(R.drawable.ic_tv, R.drawable.ic_logo_tv, R.string.tv_contract),

    @SerializedName("OTT")
    IP_TV(R.drawable.ic_ip_tv, R.drawable.ic_logo_tv, R.string.ip_tv_contract);
}