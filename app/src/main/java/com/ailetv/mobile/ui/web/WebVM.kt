package com.ailetv.mobile.ui.web

import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.ui.base.BaseViewModel

class WebVM : BaseViewModel() {
    val uiState = MutableLiveData(UiState.LOADING)
    val reloadPageLiveData = MutableLiveData<Boolean>()

    init {

    }

    fun reloadPage() {
        reloadPageLiveData.postValue(true)
    }
}