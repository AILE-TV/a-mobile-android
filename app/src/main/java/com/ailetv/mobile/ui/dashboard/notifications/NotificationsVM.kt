package com.ailetv.mobile.ui.dashboard.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.NotificationPOJO
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.NotificationsRepo
import com.ailetv.mobile.ui.base.BaseViewModel

class NotificationsVM(private val repo: NotificationsRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _isSwipeRefresh = MutableLiveData<Boolean>()
    val isSwipeRefresh: LiveData<Boolean> get() = _isSwipeRefresh

    private val _list = MutableLiveData<List<NotificationPOJO>>()
    val list: LiveData<List<NotificationPOJO>> get() = _list

    init {

    }

    fun onSwipeRefresh() {
        _isSwipeRefresh.value = true
        getNotifications()
    }

    fun getNotifications() =
        executeInBackground(_uiState, showErrorDialog = false, checkEmptyList = true) {
            repo.getNotifications().onSuccess {
                _list.value = it
            }.also { _isSwipeRefresh.value = false }
        }

    fun delete(pojo: NotificationPOJO) = executeInBackground(showProgressDialog = true) {
        repo.delete(pojo.id).onSuccess {
            _list.removeNotificationPojo(pojo)
        }
    }

    private fun MutableLiveData<List<NotificationPOJO>>.removeNotificationPojo(item: NotificationPOJO?) {
        val newList = this.value?.toMutableList() ?: mutableListOf()
        if (item != null && newList.contains(item)) {
            newList.remove(item)
            this.value = newList
        }
    }

    fun markAsRead(pojo: NotificationPOJO) = executeInBackground(showProgressDialog = true) {
        repo.markAsRead(pojo.id).onSuccess {
            pojo.isSeen?.set(true)
        }
    }
}