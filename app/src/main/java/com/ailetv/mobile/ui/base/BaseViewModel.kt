package com.ailetv.mobile.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.data.networking.Resource
import com.ailetv.mobile.data.networking.asErrorDialogModel
import com.ailetv.mobile.data.networking.asUiState
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _showErrorDialog = MutableLiveData<ErrorDialogModel?>()
    val showErrorDialog: LiveData<ErrorDialogModel?> get() = _showErrorDialog

    private val _showProgressDialog = MutableLiveData<UiState>()
    val showProgressDialog: LiveData<UiState> get() = _showProgressDialog

    fun <T> executeInBackground(
        uiState: MutableLiveData<UiState> = MutableLiveData(),
        checkEmptyList: Boolean = false,
        hasNextRequest: Boolean = false,
        checkErrorState: Boolean = true,
        showErrorDialog: Boolean = true,
        showProgressDialog: Boolean = false,
        func: suspend () -> Resource<T>
    ) {
        if (uiState.value != UiState.LOADING)
            uiState.value = UiState.LOADING

        if (showProgressDialog)
            _showProgressDialog.value = uiState.value

        viewModelScope.launch {
            val response = func()
            val newState = response.asUiState(checkEmptyList)

            if (showErrorDialog && newState == UiState.ERROR)
                showError(response.asErrorDialogModel())

            if (hasNextRequest && newState == UiState.SUCCESS)
                return@launch

            if (checkErrorState || newState != UiState.ERROR)
                uiState.value = newState

            if (showProgressDialog)
                _showProgressDialog.value = uiState.value
        }
    }

    fun showError(model: ErrorDialogModel?) {
        _showErrorDialog.value = model
    }
}