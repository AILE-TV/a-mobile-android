package com.ailetv.mobile.ui.dashboard.campaigns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.CampaignModel
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.CampaignsRepo
import com.ailetv.mobile.ui.base.BaseViewModel

class CampaignsVM(private val repo: CampaignsRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _isSwipeRefresh = MutableLiveData<Boolean>()
    val isSwipeRefresh: LiveData<Boolean> get() = _isSwipeRefresh

    private val _list = MutableLiveData<List<CampaignModel>>()
    val list: LiveData<List<CampaignModel>> get() = _list

    init {
        getCampaigns()
    }

    fun onSwipeRefresh() {
        _isSwipeRefresh.value = true
        getCampaigns()
    }

    fun getCampaigns() =
        executeInBackground(_uiState, showErrorDialog = false, checkEmptyList = true) {
            repo.getCampaigns().onSuccess {
                _list.value = it
            }.also { _isSwipeRefresh.value = false }
        }
}