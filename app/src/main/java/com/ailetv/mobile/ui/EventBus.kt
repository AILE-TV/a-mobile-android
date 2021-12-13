package com.ailetv.mobile.ui

import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.model.resource.PushNotificationModel
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.utils.SingleLiveEvent

object EventBus {
    val navigateToSplash = SingleLiveEvent<Boolean>()
    val navigateToMain = SingleLiveEvent<Boolean>()
    val refreshServices = MutableLiveData(SessionManager.userId) //CustomerId
    val refreshMain = SingleLiveEvent<Boolean>()
    val pushNotificationModel = SingleLiveEvent<PushNotificationModel>()
}