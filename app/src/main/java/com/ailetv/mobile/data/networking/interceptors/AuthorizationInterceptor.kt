package com.ailetv.mobile.data.networking.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import com.ailetv.mobile.manager.SessionManager

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("JWT", SessionManager.token)
            .build()
        return chain.proceed(request)
    }
}