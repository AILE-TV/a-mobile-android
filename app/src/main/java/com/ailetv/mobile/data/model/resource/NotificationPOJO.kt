package com.ailetv.mobile.data.model.resource

import androidx.databinding.ObservableField

data class NotificationPOJO(
    val id: Int?,
    val title: String?,
    val date: String?,
    val isSeen: ObservableField<Boolean>?
)