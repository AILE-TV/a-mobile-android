package com.ailetv.mobile.data.networking

import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.ErrorDialogModel


sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val message: String? = "",
        val statusEnum: ResponseStatusEnum = ResponseStatusEnum.FAILED
    ) : Resource<Nothing>()
}

inline fun <T : Any> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T : Any> Resource<T>.onError(action: (message: String?, statusEnum: ResponseStatusEnum?) -> Unit): Resource<T> {
    if (this is Resource.Error) action(message, statusEnum)
    return this
}

fun <T> Resource<T>.asUiState(checkEmptyList: Boolean = false): UiState {
    return when (this) {
        is Resource.Success -> if (checkEmptyList && this.data is List<*> && this.data.isNullOrEmpty()) UiState.EMPTY else UiState.SUCCESS
        is Resource.Error -> UiState.ERROR
    }
}

fun <T> Resource<T>.asErrorDialogModel(): ErrorDialogModel? {
    return when (this) {
        is Resource.Error -> ErrorDialogModel(message = this.message)
        else -> null
    }
}