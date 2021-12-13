package com.ailetv.mobile.data.networking

import com.ailetv.mobile.data.model.response.BaseResponse
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

object ResponseHandler {

    fun <T : Any> handleSuccess(baseResponse: BaseResponse, data: T?): Resource<T> {
        return if (isSuccessful(baseResponse) && data != null)
            Resource.Success(data)
        else if (data == null)
            Resource.Error(baseResponse.message, ResponseStatusEnum.NULL_DATA)
        else
            Resource.Error(baseResponse.message, baseResponse.getStatus())
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        Timber.e(e.toString())

        return when (e) {
            is HttpException -> {

                val errorJsonString = e.response()?.errorBody()?.string()

                val message = try {
                    JsonParser().parse(errorJsonString)
                        .asJsonObject["reason"]
                        .asString
                } catch (e: JsonSyntaxException) {
                    "Xəta baş verdi"
                } catch (e: IllegalStateException) {
                    "Xəta baş verdi"
                } catch (e: NullPointerException) {
                    "Xəta baş verdi"
                } catch (e: UnsupportedOperationException) {
                    "Xəta baş verdi"
                }

                Resource.Error(message)
            }

            is SocketTimeoutException ->
                Resource.Error(getErrorMessage(ErrorCodes.SocketTimeOut.code))
            else ->
                Resource.Error(getErrorMessage(Int.MAX_VALUE))
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Xəta baş verdi"
        }
    }

    private fun isSuccessful(baseResponse: BaseResponse): Boolean {
        return baseResponse.getStatus() == ResponseStatusEnum.OK
    }
}
