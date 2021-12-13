package com.ailetv.mobile.data.repo

import com.ailetv.mobile.data.asPojoList
import com.ailetv.mobile.data.networking.ResponseHandler
import com.ailetv.mobile.data.networking.api.NotificationsApiService
import com.ailetv.mobile.ui.base.BaseRepo

class NotificationsRepo(private val api: NotificationsApiService) : BaseRepo() {
    suspend fun getNotifications() = callApi {
        val response = api.getNotifications()
        ResponseHandler.handleSuccess(response, response.data.asPojoList())
    }

    suspend fun markAsRead(notificationId:Int?) = callApi {
        val response = api.markAsRead(notificationId)
        ResponseHandler.handleSuccess(response, response)
    }

    suspend fun delete(notificationId:Int?) = callApi {
        val response = api.delete(notificationId)
        ResponseHandler.handleSuccess(response, response)
    }
}