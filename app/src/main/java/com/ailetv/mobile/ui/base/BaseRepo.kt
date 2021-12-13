package com.ailetv.mobile.ui.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.ailetv.mobile.data.networking.Resource
import com.ailetv.mobile.data.networking.ResponseHandler

abstract class BaseRepo(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun <T> callApi(
        func: suspend () -> Resource<T>
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                func.invoke()
            } catch (e: Exception) {
                ResponseHandler.handleException(e)
            }
        }
    }
}