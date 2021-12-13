package com.ailetv.mobile.data.model.resource

import android.os.Parcelable
import androidx.databinding.ObservableField
import com.ailetv.mobile.data.enums.ContractTypeEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContractPOJO(
    val id: String?,
    val isActive: ObservableField<Boolean>?,
    val contractType: ContractTypeEnum?,
    val contractId: String?,
    val finalDate: String?,
    val tariff: String?,
    val balance: String?,
    val status: String?,
    val packet: String?
) : Parcelable {

    override fun toString() = "$contractType $tariff $status"
}
