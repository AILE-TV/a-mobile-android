package com.ailetv.mobile.ui.dashboard.myAccount

import com.ailetv.mobile.data.networking.onSuccess
import com.ailetv.mobile.data.repo.MyAccountRepo
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.ui.base.BaseViewModel

class MyAccountVM(private val repo: MyAccountRepo) : BaseViewModel() {
    fun logout() = executeInBackground(showProgressDialog = true) {
        repo.logout().onSuccess {
            SessionManager.clearData(true)
        }
    }
}