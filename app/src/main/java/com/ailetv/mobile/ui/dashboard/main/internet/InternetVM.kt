package com.ailetv.mobile.ui.dashboard.main.internet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.repo.PaymentRepo
import com.ailetv.mobile.ui.base.BaseViewModel
import com.ailetv.mobile.utils.extensions.getFormattedDate

class InternetVM(private val repo: PaymentRepo, private val pojo: ContractPOJO) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _finalDate = MutableLiveData<String?>()
    val finalDate: LiveData<String?> get() = _finalDate

    private val _balance = MutableLiveData<String?>()
    val balance: LiveData<String?> get() = _balance

    private val _tariff = MutableLiveData<String?>()
    val tariff: LiveData<String?> get() = _tariff

    private val _packet = MutableLiveData<String?>()
    val packet: LiveData<String?> get() = _packet

    private val _status = MutableLiveData<String?>()
    val status: LiveData<String?> get() = _status

    init {
        _finalDate.value = pojo.finalDate.getFormattedDate("d/MM/yyyy", currentFormat = "dd.MM.yyyy")
        _balance.value = pojo.balance
        _tariff.value = pojo.tariff
        _packet.value = pojo.packet ?: ""
        _status.value = pojo.status
    }
}