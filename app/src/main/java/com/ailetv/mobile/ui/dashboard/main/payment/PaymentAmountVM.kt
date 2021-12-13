package com.ailetv.mobile.ui.dashboard.main.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.enums.PaymentStatus
import com.ailetv.mobile.data.model.request.PaymentCreateRequest
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.PaymentRepo
import com.ailetv.mobile.ui.base.BaseViewModel
import com.ailetv.mobile.utils.SingleLiveEvent
import com.ailetv.mobile.utils.extensions.toDoubleOrZero

class PaymentAmountVM(private val repo: PaymentRepo, private val pojo: ContractPOJO) :
    BaseViewModel() {

    val amount = MutableLiveData<String?>()

    private val _paymentCreated = SingleLiveEvent<Pair<String, String?>>()
    val paymentCreated: LiveData<Pair<String, String?>> get() = _paymentCreated

    private val _paymentSuccess = SingleLiveEvent<Boolean>()
    val paymentSuccess: LiveData<Boolean> get() = _paymentSuccess

    private val _paymentUnSuccess = SingleLiveEvent<Boolean>()
    val paymentUnSuccess: LiveData<Boolean> get() = _paymentUnSuccess

    private var paymentId: Int? = 0

    init {

    }

    fun paymentCreate() = executeInBackground(showProgressDialog = true) {
        val request =
            PaymentCreateRequest(
                contractType = pojo.contractType,
                contractId = pojo.contractId,
                amount = (amount.value.toDoubleOrZero() * 100).toInt()
            )

        repo.create(request).onSuccess {
            paymentId = it.paymentId

            _paymentCreated.value = Pair(it.redirectUrl ?: "", it.postData)
        }
    }

    fun paymentStatus() {
        if (paymentId != 0)
            executeInBackground(showProgressDialog = true) {
                repo.getStatus(paymentId).onSuccess {
                    if (PaymentStatus.byStatus(it.status) == PaymentStatus.SUCCESSFUL)
                        _paymentSuccess.value = true
                    else
                        _paymentUnSuccess.value = true
                }
            }
    }
}