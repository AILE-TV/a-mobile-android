package com.ailetv.mobile.data.networking.api

import com.ailetv.mobile.BuildConfig
import com.ailetv.mobile.data.networking.interceptors.AuthorizationInterceptor
import com.ailetv.mobile.data.networking.interceptors.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object AileTvApiClient {
    fun provideAuthApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(AuthApiService::class.java)

    fun provideMainApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(MainApiService::class.java)

    fun provideCampaignsApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(CampaignsApiService::class.java)

    fun provideNotificationsApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(NotificationsApiService::class.java)

    fun provideServicesApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(ServicesApiService::class.java)

    fun provideMyAccountApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(MyAccountApiService::class.java)

    fun providePaymentApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(PaymentApiService::class.java)

    fun provideOthersApi() =
        getRetrofit("${BuildConfig.BASE_URL}${version()}").create(OthersApiService::class.java)


    private fun getRetrofit(baseUrl: String): Retrofit {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor.getInterceptor())
            .build()

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    private fun version() = ""
}