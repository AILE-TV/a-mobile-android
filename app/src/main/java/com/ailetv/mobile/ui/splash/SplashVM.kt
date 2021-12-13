package com.ailetv.mobile.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.AuthRepo
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashVM(private val repo: AuthRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _isUserLoggedIn = MutableSharedFlow<Boolean>()
    val isUserLoggedIn = _isUserLoggedIn.asSharedFlow()

    private val _hasForceUpdate = MutableStateFlow(false)
    val hasForceUpdate = _hasForceUpdate.asStateFlow()


    init {
        checkAppVersion()
    }

    fun checkAppVersion() = executeInBackground(_uiState, showErrorDialog = false) {
        repo.checkAppVersion().onSuccess {
            _hasForceUpdate.emit(it)

            if (!it)
                continueSplash()
        }
    }

    private fun continueSplash() {
        viewModelScope.launch {
            delay(1000)

            _isUserLoggedIn.emit(SessionManager.loggedIn)
        }
    }
}