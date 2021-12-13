package com.ailetv.mobile.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.model.request.LoginRequest
import com.ailetv.mobile.data.model.resource.util.AuthUtilModel
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.AuthRepo
import com.ailetv.mobile.ui.base.BaseViewModel
import com.ailetv.mobile.utils.extensions.trimSpace

class LoginVM(private val repo: AuthRepo) : BaseViewModel() {
    private val _navigateToNext = MutableLiveData<AuthUtilModel>()
    val navigateToNext: LiveData<AuthUtilModel> get() = _navigateToNext

    val phoneNumber = MutableLiveData<String>()
    val countryCode = MutableLiveData<String>()

    fun login() = executeInBackground(showProgressDialog = true) {
        val request =
            LoginRequest(phoneNumber = "${countryCode.value?.removePrefix("+")}${phoneNumber.value}".trimSpace())

        repo.login(request).onSuccess {
            val utilModel =
                AuthUtilModel(
                    userId = it.userId,
                    phoneNumber = request.phoneNumber,
                    phoneNumberMasked = "${countryCode.value} ${phoneNumber.value}"
                )

            _navigateToNext.value = utilModel
        }
    }
}