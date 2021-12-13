package com.ailetv.mobile.data.networking.api

import retrofit2.http.GET
import retrofit2.http.Path
import com.ailetv.mobile.data.model.response.BaseResponse
import com.ailetv.mobile.data.model.response.NotificationsResponse

interface NotificationsApiService {
    @GET("notifications")
    suspend fun getNotifications(): NotificationsResponse

    @GET("notifications/markasread/{notificationId}")
    suspend fun markAsRead(@Path("notificationId") notificationId: Int?): BaseResponse

    @GET("notifications/delete/{notificationId}")
    suspend fun delete(@Path("notificationId") notificationId: Int?): BaseResponse
}
