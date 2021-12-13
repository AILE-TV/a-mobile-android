package com.ailetv.mobile.ui.auth.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.data.model.request.CheckOtpRequest
import com.ailetv.mobile.data.model.request.ResendOtpRequest
import com.ailetv.mobile.data.model.resource.util.AuthUtilModel
import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.AuthRepo
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.ui.base.BaseViewModel
import com.ailetv.mobile.utils.CombinedLiveData
import com.ailetv.mobile.utils.extensions.isValidOtp
import com.ailetv.mobile.utils.extensions.toIntOrZero
import countDownTimer

class OtpVM(private val repo: AuthRepo, private val utilModel: AuthUtilModel) : BaseViewModel() {
    private val _navigateToNext = MutableLiveData<Boolean>()
    val navigateToNext: LiveData<Boolean> get() = _navigateToNext

    val otp = MutableLiveData<String>()
    val resendEnable = MutableLiveData(true)
    val verifyButtonEnable = CombinedLiveData(otp) { checkFields() }

    fun checkOtp() = executeInBackground(showProgressDialog = true) {
        val request = CheckOtpRequest(userId = utilModel.userId, otp = otp.value.toIntOrZero())

        repo.checkOtp(request).onSuccess {
            SessionManager.setUserData(
                phoneNumber = utilModel.phoneNumberMasked ?: "",
                userId = it.userId,
                token = it.token,
                loggedIn = true,
            )

            _navigateToNext.value = true
        }
    }

    fun resendOtp() = executeInBackground(showProgressDialog = true) {
        val request =
            ResendOtpRequest(userId = utilModel.userId, phoneNumber = utilModel.phoneNumber)

        repo.resendOtp(request).onSuccess {
            resendEnable.value = false

            countDownTimer(30000) {
                resendEnable.value = true
            }
        }
    }

    private fun checkFields() =
        otp.value.isValidOtp()
}