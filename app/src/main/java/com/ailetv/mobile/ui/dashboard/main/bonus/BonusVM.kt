package com.ailetv.mobile.ui.dashboard.main.bonus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.BonusRepo
import com.ailetv.mobile.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BonusVM(private val repo: BonusRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _payEnable = MutableStateFlow(false)
    val payEnable = _payEnable.asStateFlow()

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String> get() = _balance

    private val _transferSuccess = MutableSharedFlow<Boolean>()
    val transferSuccess = _transferSuccess.asSharedFlow()

    private val _contractPOJO = MutableStateFlow<ContractPOJO?>(null)
    val contractPOJO = _contractPOJO.asStateFlow()

    init {
        getBonusBalance()
    }

    fun getBonusBalance() = executeInBackground(_uiState) {
        repo.getBonusBalance().onSuccess {
            _balance.value = it.bonusBalance.toString()
            _payEnable.emit(it.payBonus == true)
        }
    }

    fun transferBonus() = executeInBackground(showProgressDialog = true) {
        repo.payBonus(id = contractPOJO.value?.contractId).onSuccess {
            _transferSuccess.emit(true)
        }
    }

    fun getBonusHint() = executeInBackground(showProgressDialog = true) {
        repo.getBonusHint().onSuccess {
            showError(ErrorDialogModel(message = it))
        }
    }

    fun setContractPOJO(pojo: ContractPOJO?) {
        viewModelScope.launch {
            _contractPOJO.emit(pojo)
        }
    }
}