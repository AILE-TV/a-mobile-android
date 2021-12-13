package com.ailetv.mobile.ui.dashboard.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.ServicePOJO
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.ServicesRepo
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseViewModel

class ServicesVM(private val repo: ServicesRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _isSwipeRefresh = MutableLiveData<Boolean>()
    val isSwipeRefresh: LiveData<Boolean> get() = _isSwipeRefresh

    private val _list = MutableLiveData<List<ServicePOJO>>()
    val list: LiveData<List<ServicePOJO>> get() = _list


    init {

    }

    fun onSwipeRefresh() {
        _isSwipeRefresh.value = true
        getServices()
    }

    fun getServices() =
        executeInBackground(_uiState, showErrorDialog = false, checkEmptyList = true) {
            val customerId = EventBus.refreshServices.value

            repo.getServices(customerId).onSuccess {
                _list.value = it
            }.also { _isSwipeRefresh.value = false }
        }
}