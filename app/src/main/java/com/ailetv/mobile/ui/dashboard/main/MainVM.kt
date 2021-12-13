package com.ailetv.mobile.ui.dashboard.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.data.model.request.ContractRequest
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.model.resource.CustomerModel
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.MainRepo
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseViewModel

class MainVM(private val repo: MainRepo) : BaseViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _contractsUiState = MutableLiveData<UiState>()
    val contractsUiState: LiveData<UiState> get() = _contractsUiState

    private val _customerList = MutableLiveData<List<CustomerModel>>()
    val customerList: LiveData<List<CustomerModel>> get() = _customerList

    private val _contractList = MutableLiveData<List<ContractPOJO>>()
    val contractList: LiveData<List<ContractPOJO>> get() = _contractList

    private val _customer = MutableLiveData<CustomerModel?>()
    val customer: LiveData<CustomerModel?> get() = _customer

    private val _bonusBalance = MutableLiveData<String>()
    val bonusBalance: LiveData<String?> get() = _bonusBalance

    init {
        getCustomerListData()
    }

    fun getCustomerListData() = executeInBackground(_uiState, hasNextRequest = true) {
        repo.getCustomerList().onSuccess {
            _customerList.value = it

            setCustomerModel(it.getOrNull(0))
            getBonusBalanceData()
        }
    }

    private fun getBonusBalanceData() = executeInBackground(_uiState) {
        repo.getBonusBalance().onSuccess {
            _bonusBalance.value = it.bonusBalance.toString()
        }
    }

    fun getContractListData() =
        executeInBackground(_contractsUiState, showErrorDialog = false, checkEmptyList = true) {
            val request = ContractRequest(customer.value?.customerId)

            repo.getContractList(request).onSuccess {
                _contractList.value = it
            }
        }

    fun setCustomerModel(model: CustomerModel?) {
        model?.let {
            EventBus.refreshServices.value = it.customerId
            _customer.value = model

            getContractListData()
        }
    }
}